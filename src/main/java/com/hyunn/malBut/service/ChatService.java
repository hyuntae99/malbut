package com.hyunn.malBut.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  @Value("${chatgpt.api-key}")
  private String OPENAI_API_KEY;

  @Value("${chatgpt.model}")
  private String MODEL_NAME;

  @Value("${chatgpt.url}")
  private String OPENAI_API_URL;

  @Value("${chatgpt.prompt.chatTemplate}")
  private String PROMPT_CHAT_TEMPLATE;

  private final RestTemplate restTemplate;
  private final Map<String, List<Map<String, String>>> sessionStorage = new HashMap<>(); // 세션 저장소를 대화 기록 저장소로 수정
  private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 처리

  /**
   * gpt 챗봇 초기 설정
   */
  @Transactional
  public String processChat(String sessionId, String userInput, String apiKey) {
    // API KEY 유효성 검사
    if (apiKey == null || !apiKey.equals(xApiKey)) {
      throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
    }

    // 세션이 없으면 초기화
    if (!sessionStorage.containsKey(sessionId)) {
      sessionStorage.put(sessionId, new ArrayList<>()); // 대화 기록을 리스트로 관리
      Map<String, String> initialPrompt = new HashMap<>();

      initialPrompt.put("role", "system");
      String content = PROMPT_CHAT_TEMPLATE.replace("{userInput}", userInput);
      initialPrompt.put("content", content);
      sessionStorage.get(sessionId).add(initialPrompt);
    }

    // 사용자 메시지 추가
    Map<String, String> userMessage = new HashMap<>();
    userMessage.put("role", "user");
    userMessage.put("content", userInput);
    sessionStorage.get(sessionId).add(userMessage);

    // OpenAI API 호출
    return callOpenAiApi(sessionId);
  }

  /**
   * gpt 챗봇 대화
   */
  @Transactional
  public String callOpenAiApi(String sessionId) {
    // 세션 메시지 가져오기
    List<Map<String, String>> messages = sessionStorage.get(sessionId);

    // 요청 바디 설정
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", MODEL_NAME);
    requestBody.put("messages", messages);

    // HTTP 요청 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
    headers.set("Content-Type", "application/json");

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    try {
      // OpenAI API 호출
      ResponseEntity<String> response = restTemplate.exchange(
          OPENAI_API_URL,
          HttpMethod.POST,
          entity,
          String.class
      );

      // 응답 처리
      JsonNode jsonNode = objectMapper.readTree(response.getBody());
      String botReply = jsonNode.get("choices").get(0).get("message").get("content").asText();

      // 대화 문맥에 봇 응답 추가
      Map<String, String> botMessage = new HashMap<>();
      botMessage.put("role", "assistant");
      botMessage.put("content", botReply);
      sessionStorage.get(sessionId).add(botMessage);  // GPT의 응답 기억

      return botReply;

    } catch (Exception e) {
      e.printStackTrace();
      return "Error in processing GPT response.";
    }
  }
}
