package com.hyunn.malBut.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunn.malBut.dto.response.FlaskEvaluationResponse;
import com.hyunn.malBut.dto.response.PronunciationResponse;
import com.hyunn.malBut.dto.response.ScriptResponse;
import com.hyunn.malBut.entity.Pronunciation;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.exception.ApiNotFoundException;
import com.hyunn.malBut.repository.PronunciationJpaRepository;
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
public class FlaskPronunciationService {

    @Value("${spring.security.x-api-key}")
    private String xApiKey;

    @Value("${google.api-key}")
    private String GOOGLE_API_KEY;

    @Value("${google.stt-url}")
    private String GOOGLE_ASR_URL;

    @Value("${flask.url}")
    private String flaskApiUrl;

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

        // Flask API에 대본과 변환된 텍스트를 보내어 평가 요청
        FlaskEvaluationResponse flaskResponse = callFlaskPronunciationEvaluation(script, recognizedText);

        // Flask 응답에서 점수 추출
        double finalScore = flaskResponse.getFinal_score();

        // 점수에 따른 평가 메시지 생성
        String evaluationMessage = generateEvaluationMessage(finalScore);

        // DTO로 반환
        return PronunciationResponse.create((int) Math.round(finalScore), evaluationMessage);
    }

    /**
     * Flask API 호출하여 발음 평가
     */
    private FlaskEvaluationResponse callFlaskPronunciationEvaluation(String script, String recognizedText)
            throws Exception {
        // 요청 데이터 생성
        Map<String, String> requestData = new HashMap<>();
        requestData.put("sentence1", script);
        requestData.put("sentence2", recognizedText);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔티티 생성
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestData, headers);

        // Flask API 호출
        ResponseEntity<FlaskEvaluationResponse> responseEntity = restTemplate.postForEntity(
                flaskApiUrl,
                requestEntity,
                FlaskEvaluationResponse.class
        );

        // Flask 응답 확인
        FlaskEvaluationResponse flaskResponse = responseEntity.getBody();

        if (flaskResponse == null) {
            throw new ApiNotFoundException("Flask 서버로부터 응답이 없습니다.");
        }

        return flaskResponse;
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
     * 점수에 따른 평가 메시지 생성
     */
    private String generateEvaluationMessage(double score) {
        if (score >= 90) {
            return "Excellent pronunciation!";
        } else if (score >= 70) {
            return "Good job, but there's room for improvement.";
        } else if (score >= 50) {
            return "Not bad, but keep practicing.";
        } else if (score >= 30) {
            return "Needs improvement. Keep practicing!";
        } else {
            return "I think the voice wasn't recognized well.";
        }
    }

}
