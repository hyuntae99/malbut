package com.hyunn.malBut.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunn.malBut.dto.response.PronunciationResponse;
import com.hyunn.malBut.dto.response.ScriptResponse;
import com.hyunn.malBut.entity.Pronunciation;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.repository.PronunciationJpaRepository;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PronunciationService {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  @Value("${google.api-key}")
  private String GOOGLE_API_KEY;

  @Value("${google.stt-url}")
  private String GOOGLE_ASR_URL;

  @Value("${chatgpt.api-key}")
  private String OPENAI_API_KEY;

  @Value("${chatgpt.model}")
  private String MODEL_NAME;

  @Value("${chatgpt.url}")
  private String OPENAI_API_URL;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final RestTemplate restTemplate;
  private final PronunciationJpaRepository pronunciationRepository;

  /**
   * 난이도에 맞는 무작위 대본을 반환
   */
  @Transactional
  public ScriptResponse getRandomScript(String level) {
    List<Pronunciation> scripts = pronunciationRepository.findByLevel(level);

    if (scripts.isEmpty()) {
      throw new IllegalArgumentException("해당 난이도의 대본이 없습니다.");
    }

    Random random = new Random();
    Pronunciation selectedScript = scripts.get(random.nextInt(scripts.size()));

    // 대본과 함께 MP3 링크도 반환
    return ScriptResponse.create(selectedScript.getKorean(), selectedScript.getLink());
  }

  /**
   * 발음 평가 함수
   */
  @Transactional
  public PronunciationResponse evaluatePronunciation(MultipartFile audioFile, String script,
      String apiKey) throws Exception {
    // API KEY 유효성 검사
    if (apiKey == null || !apiKey.equals(xApiKey)) {
      throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
    }

    // Google STT로 음성을 텍스트로 변환
    String recognizedText = convertSpeechToText(audioFile);

    // GPT에 대본과 변환된 텍스트를 비교하여 평가 요청
    String gptResponse = compareWithGpt(script, recognizedText);

    // GPT 응답을 파싱하여 점수 및 개선점 추출
    JsonNode gptJsonResponse = objectMapper.readTree(gptResponse);
    String content = gptJsonResponse.get("choices").get(0).get("message").get("content").asText();

    // 수동으로 score와 improvements 추출
    String score = content.substring(content.indexOf("Score: ") + 7, content.indexOf("\n")).trim();
    String improvements = content.substring(content.indexOf("Improvement: ") + 13).trim();

    // 점수를 정수로 변환하고 DTO로 반환
    int rank = extractScore(score);

    return PronunciationResponse.create(rank, improvements);
  }

  /**
   * STT
   */
  @Transactional
  public String convertSpeechToText(MultipartFile audioFile) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // Google Speech-to-Text 요청 바디 설정
    String requestBody =
        "{ \"config\": { \"languageCode\": \"ko-KR\" }, \"audio\": { \"content\": \"" +
            Base64.getEncoder().encodeToString(audioFile.getBytes()) + "\" }}";

    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> response = restTemplate.exchange(
        GOOGLE_ASR_URL + GOOGLE_API_KEY,
        HttpMethod.POST,
        entity,
        String.class
    );

    JsonNode jsonNode = objectMapper.readTree(response.getBody());
    return jsonNode.get("results").get(0).get("alternatives").get(0).get("transcript").asText();
  }

  /**
   * 대본과 텍스트를 상호 평가
   */
  @Transactional
  public String compareWithGpt(String script, String recognizedText) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
    headers.set("Content-Type", "application/json");

    // GPT 요청 바디 설정
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", MODEL_NAME);

    List<Map<String, String>> messages = new ArrayList<>();
    Map<String, String> userPrompt = new HashMap<>();
    userPrompt.put("role", "user");
    userPrompt.put("content",
        "Here is the script: \"" + script + "\" and here is the recognized text: \"" +
            recognizedText
            + "\". Compare the two and provide feedback, rate the pronunciation out of 100, and suggest improvements. \n"
            + "Please provide it in the form of \"Score:\" \"Improvement:\"\n"
            + "Improvements should be expressed in one sentence to sum up the most important part, Please give it a very very generous score since it was pronounced by a foreigner."
            + "If the evaluation score is 90 or higher, please give praise instead of suggestions for improvement.");
    messages.add(userPrompt);

    requestBody.put("messages", messages);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> response = restTemplate.exchange(
        OPENAI_API_URL,
        HttpMethod.POST,
        entity,
        String.class
    );

    return response.getBody();
  }

  /**
   * 숫자 파싱 함수
   */
  public Integer extractScore(String scoreString) {
    if (scoreString.contains("/")) {
      // '/' 기호 앞의 숫자만 추출
      return Integer.parseInt(scoreString.split("/")[0].trim());
    } else {
      // 일반적인 숫자 처리
      return Integer.parseInt(scoreString.trim());
    }
  }
}
