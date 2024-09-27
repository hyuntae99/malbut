package com.hyunn.malBut.service;

import com.hyunn.malBut.dto.request.EvaluateProverbRequest;
import com.hyunn.malBut.dto.request.GradeWordRequest;
import com.hyunn.malBut.dto.response.EvaluateProverbResponse;
import com.hyunn.malBut.dto.response.QuizProverbResponse;
import com.hyunn.malBut.dto.response.QuizWordResponse;
import com.hyunn.malBut.entity.Proverb;
import com.hyunn.malBut.entity.Word;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.repository.ProverbJpaRepository;
import com.hyunn.malBut.repository.WordJpaRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  private final WordJpaRepository wordRepository;
  private final ProverbJpaRepository proverbJpaRepository;

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
        easyCount = 20;
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
        easyCount = 10;
        break;
      case "intermediate":
        easyCount = 8;
        hardCount = 2;
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
    List<Proverb> hardProverbs = proverbJpaRepository.findRandomEasyProverbs(hardCount);

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
  public List<EvaluateProverbResponse> gradeProverbQuiz(List<EvaluateProverbRequest> answers, String apiKey) {
    // API KEY 유효성 검사
    if (apiKey == null || !apiKey.equals(xApiKey)) {
      throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
    }

    // flask에 요청을 보내야함

    // 응답 파싱

    return null;
  }

}
