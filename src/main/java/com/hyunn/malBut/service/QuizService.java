package com.hyunn.malBut.service;

import com.hyunn.malBut.dto.request.EvaluateProverbRequest;
import com.hyunn.malBut.dto.request.GradeWordRequest;
import com.hyunn.malBut.dto.response.EvaluateProverbResponse;
import com.hyunn.malBut.dto.response.FlaskEvaluationResponse;
import com.hyunn.malBut.dto.response.QuizProverbResponse;
import com.hyunn.malBut.dto.response.QuizWordResponse;
import com.hyunn.malBut.entity.Proverb;
import com.hyunn.malBut.entity.Word;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.exception.ApiNotFoundException;
import com.hyunn.malBut.repository.ProverbJpaRepository;
import com.hyunn.malBut.repository.WordJpaRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Value("${spring.security.x-api-key}")
    private String xApiKey;

    @Value("${flask.url}")
    private String flaskApiUrl;

    private final WordJpaRepository wordRepository;
    private final ProverbJpaRepository proverbJpaRepository;
    private final RestTemplate restTemplate;

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
            // 요청 데이터 생성
            List<Map<String, String>> requestData = new ArrayList<>();
            for (EvaluateProverbRequest answer : answers) {
                if (answer.getQuestion() == null || answer.getCorrectAnswer() == null
                        || answer.getUserAnswer() == null) {
                    throw new ApiNotFoundException("Question, correctAnswer, and userAnswer must not be null.");
                }

                // 유저 응답 문장 생성
                String userAnswer = answer.getQuestion().replace("_", answer.getUserAnswer());

                Map<String, String> sentencePair = new HashMap<>();
                sentencePair.put("sentence1", answer.getCorrectAnswer());
                sentencePair.put("sentence2", userAnswer);
                requestData.add(sentencePair);
            }

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 엔티티 생성
            HttpEntity<List<Map<String, String>>> requestEntity = new HttpEntity<>(requestData, headers);

            // Flask API 호출
            ResponseEntity<FlaskEvaluationResponse[]> responseEntity = restTemplate.postForEntity(
                    flaskApiUrl, requestEntity, FlaskEvaluationResponse[].class);

            // Flask API 호출 후
            FlaskEvaluationResponse[] responseArray = responseEntity.getBody();

            if (responseArray == null) {
                throw new ApiNotFoundException("No response from Flask server.");
            }

            List<FlaskEvaluationResponse> flaskResponses = Arrays.asList(responseArray);

            // 결과 리스트 생성
            List<EvaluateProverbResponse> resultList = new ArrayList<>();

            // 응답 매핑
            for (int i = 0; i < flaskResponses.size(); i++) {
                FlaskEvaluationResponse flaskResponse = flaskResponses.get(i);

                int score = (int) Math.round(flaskResponse.getFinal_score());

                String evaluate = evaluateProverb(score, apiKey);

                EvaluateProverbResponse response = EvaluateProverbResponse.create(
                        flaskResponse.getSentence1(), // 정답 문장
                        flaskResponse.getSentence2(), // 사용자 문장
                        score, // 점수
                        evaluate // 평가 문구
                );
                resultList.add(response);
            }

            return resultList;

        } catch (Exception e) {
            throw new ApiNotFoundException("Error calling Flask API: " + e.getMessage());
        }
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

    /**
     * 점수에 대한 평가표
     */
    public String evaluateProverb(int score, String apiKey) {
        // API KEY 유효성 검사
        if (apiKey == null || !apiKey.equals(xApiKey)) {
            throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
        }

        if (score == 100) {
            return "Perfect!";
        } else if (score >= 80) {
            return "Good Job!";
        } else if (score >= 60) {
            return "Not Accurate";
        } else {
            return "That's Wrong";
        }
    }
}
