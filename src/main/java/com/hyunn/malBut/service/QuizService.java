package com.hyunn.malBut.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunn.malBut.dto.request.EvaluateProverbRequest;
import com.hyunn.malBut.dto.request.GradeWordRequest;
import com.hyunn.malBut.dto.response.EvaluateProverbResponse;
import com.hyunn.malBut.dto.response.QuizProverbResponse;
import com.hyunn.malBut.dto.response.QuizWordResponse;
import com.hyunn.malBut.entity.Proverb;
import com.hyunn.malBut.entity.Word;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.exception.ApiNotFoundException;
import com.hyunn.malBut.repository.ProverbJpaRepository;
import com.hyunn.malBut.repository.WordJpaRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
public class QuizService {

    @Value("${spring.security.x-api-key}")
    private String xApiKey;

    @Value("${chatgpt.api-key}")
    private String OPENAI_API_KEY;

    @Value("${chatgpt.model}")
    private String MODEL_NAME;

    @Value("${chatgpt.url}")
    private String OPENAI_API_URL;

    @Value("${chatgpt.prompt.proverbTemplate1}")
    private String PROMPT_FEEDBACK_TEMPLATE1;

    @Value("${chatgpt.prompt.proverbTemplate2}")
    private String PROMPT_FEEDBACK_TEMPLATE2;

    private final WordJpaRepository wordRepository;
    private final ProverbJpaRepository proverbJpaRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 처리

    /**
     * 20개의 문제를 난이도에 맞게 단어 퀴즈를 생성
     */
    @Transactional
    public List<QuizWordResponse> startWordQuiz(String level, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        List<QuizWordResponse> quiz = new ArrayList<>();

        // 난이도에 따른 문제 비율 설정
        int easyCount = 0;
        int hardCount = 0;

        switch (level.toLowerCase()) {
            case "beginner":
                easyCount = 19;
                hardCount = 1;
                break;
            case "intermediate":
                easyCount = 15;
                hardCount = 5;
                break;
            case "advanced":
                easyCount = 5;
                hardCount = 15;
                break;
            default:
                // 발생할 일은 없음
                throw new IllegalArgumentException("잘못된 난이도: " + level);
        }

        // 쉬운 문제와 어려운 문제를 랜덤으로 필요한 수만큼 가져오기
        List<Word> easyWords = wordRepository.findRandomEasyWords(easyCount);
        List<Word> hardWords = wordRepository.findRandomHardWords(hardCount);

        // 쉬운 문제 추가
        for (Word word : easyWords) {
            quiz.add(generateWordQuestion(word, easyWords));
        }

        // 어려운 문제 추가
        for (Word word : hardWords) {
            quiz.add(generateWordQuestion(word, hardWords));
        }

        return quiz;
    }

    /**
     * 단어 퀴즈 선지 생성 함수
     */
    private QuizWordResponse generateWordQuestion(Word correctWord, List<Word> wordList) {
        Random random = new Random();
        List<String> choices = new ArrayList<>();
        choices.add(correctWord.getEnglish());

        while (choices.size() < 4) {
            Word randomWord = wordList.get(random.nextInt(wordList.size()));
            if (!choices.contains(randomWord.getEnglish())) {
                choices.add(randomWord.getEnglish());
            }
        }

        Collections.shuffle(choices);

        return QuizWordResponse.create(correctWord.getKorean(), choices, correctWord.getEnglish());
    }


    /**
     * 단어 퀴즈 채점 및 평가
     */
    public int gradeWordQuiz(List<GradeWordRequest> answers, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        int score = 0;

        for (GradeWordRequest answer : answers) {
            if (answer.getUserAnswer().equals(answer.getCorrectAnswer())) {
                score += 5;
            }
        }

        return score;
    }

    /**
     * 10개의 문제를 난이도에 맞게 속담/숙어 퀴즈를 생성
     */
    @Transactional
    public List<QuizProverbResponse> startProverbQuiz(String level, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        List<QuizProverbResponse> quiz = new ArrayList<>();

        // 난이도에 따른 문제 비율 설정
        int easyCount = 0;
        int hardCount = 0;

        switch (level.toLowerCase()) {
            case "beginner":
                easyCount = 9;
                hardCount = 1;
                break;
            case "intermediate":
                easyCount = 7;
                hardCount = 3;
                break;
            case "advanced":
                easyCount = 5;
                hardCount = 5;
                break;
            default:
                // 발생할 일 없음
                throw new IllegalArgumentException("잘못된 난이도: " + level);
        }

        // 쉬운 문제와 어려운 문제를 랜덤으로 필요한 수만큼 가져오기
        List<Proverb> easyProverbs = proverbJpaRepository.findRandomEasyProverbs(easyCount);
        List<Proverb> hardProverbs = proverbJpaRepository.findRandomHardProverbs(hardCount);

        // 쉬운 문제 추가
        for (Proverb easyProverb : easyProverbs) {
            QuizProverbResponse response = QuizProverbResponse.create(easyProverb.getEnglish(),
                    easyProverb.getKorean(), easyProverb.getQuestion());
            quiz.add(response);
        }

        // 어려운 문제 추가
        for (Proverb hardProverb : hardProverbs) {
            QuizProverbResponse response = QuizProverbResponse.create(hardProverb.getEnglish(),
                    hardProverb.getKorean(), hardProverb.getQuestion());
            quiz.add(response);
        }

        return quiz;
    }

    /**
     * 속담/구문 퀴즈 채점 및 평가
     */
    public List<EvaluateProverbResponse> gradeProverbQuiz(
            List<EvaluateProverbRequest> answers, String apiKey) {

        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        try {
            // GPT를 호출하여 평가
            String gptResponse = evaluateProverbsWithGpt(answers);

            // GPT 응답 파싱
            List<EvaluateProverbResponse> resultList = parseGptResponse(gptResponse, answers);

            return resultList;

        } catch (Exception e) {
            throw new ApiNotFoundException("GPT API 호출 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * GPT에 속담 평가 요청
     */
    private String evaluateProverbsWithGpt(List<EvaluateProverbRequest> answers) throws Exception {
        // 프롬프트 생성
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(PROMPT_FEEDBACK_TEMPLATE1);

        int index = 1;
        for (EvaluateProverbRequest answer : answers) {
            if (answer.getQuestion() == null || answer.getCorrectAnswer() == null || answer.getUserAnswer() == null) {
                throw new ApiNotFoundException("Question, correctAnswer, userAnswer는 null이 아니어야 합니다.");
            }

            // 유저 응답 문장 생성
            String userAnswer = answer.getQuestion().replace("_", answer.getUserAnswer());

            promptBuilder.append(index).append(".\n");
            promptBuilder.append("Correct Answer: ").append(answer.getCorrectAnswer()).append("\n");
            promptBuilder.append("User Answer: ").append(userAnswer).append("\n\n");

            index++;
        }

        promptBuilder.append(PROMPT_FEEDBACK_TEMPLATE2);

        // GPT API 호출
        String gptResponse = callGptApi(promptBuilder.toString());

        return gptResponse;
    }

    /**
     * GPT API 호출
     */
    private String callGptApi(String prompt) {
        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.set("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", MODEL_NAME);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userPrompt = new HashMap<>();

        userPrompt.put("role", "user");
        userPrompt.put("content", prompt);

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
     * GPT 응답 파싱
     */
    private List<EvaluateProverbResponse> parseGptResponse(String gptResponse,
                                                           List<EvaluateProverbRequest> answers) throws Exception {
        // GPT 응답 파싱
        JsonNode gptJsonResponse = objectMapper.readTree(gptResponse);
        String content = gptJsonResponse.get("choices").get(0).get("message").get("content").asText();

        // JSON 부분만 추출
        int jsonStartIndex = content.indexOf("{");
        int jsonEndIndex = content.lastIndexOf("}") + 1;
        String jsonString = content.substring(jsonStartIndex, jsonEndIndex);

        JsonNode jsonResponse = objectMapper.readTree(jsonString);
        JsonNode resultsArray = jsonResponse.get("results");

        if (!resultsArray.isArray()) {
            throw new ApiNotFoundException("GPT 응답이 올바른 JSON 형식이 아닙니다.");
        }

        List<EvaluateProverbResponse> resultList = new ArrayList<>();

        for (int i = 0; i < resultsArray.size(); i++) {
            JsonNode resultNode = resultsArray.get(i);

            int score = resultNode.get("score").asInt();
            String feedback = resultNode.get("feedback").asText();

            EvaluateProverbRequest answer = answers.get(i);
            String userAnswer = answer.getQuestion().replace("_", answer.getUserAnswer());

            EvaluateProverbResponse response = EvaluateProverbResponse.create(
                    answer.getCorrectAnswer(),
                    userAnswer,
                    score,
                    feedback
            );

            resultList.add(response);
        }

        return resultList;
    }

    /**
     * 점수에 대한 등급표
     */
    public String calculateWordGrade(int score, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        if (score >= 80) {
            return "A";
        } else if (score >= 60) {
            return "B";
        } else if (score >= 40) {
            return "C";
        } else if (score >= 20) {
            return "D";
        } else {
            return "F";
        }
    }
}
