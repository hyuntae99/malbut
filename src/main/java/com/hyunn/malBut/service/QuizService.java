package com.hyunn.malBut.service;

import com.hyunn.malBut.dto.quiz.Answer;
import com.hyunn.malBut.dto.quiz.Question;
import com.hyunn.malBut.entity.Word;
import com.hyunn.malBut.exception.ApiKeyNotValidException;
import com.hyunn.malBut.repository.WordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizService {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  private final WordJpaRepository wordRepository;

  /**
   * 20개의 문제를 난이도에 맞게 생성
   */
  @Transactional
  public List<Question> startQuiz(String level, String apiKey) {
    // API KEY 유효성 검사
    if (apiKey == null || !apiKey.equals(xApiKey)) {
      throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
    }

    List<Question> quiz = new ArrayList<>();

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
      quiz.add(generateQuestion(word, easyWords));
    }

    // 어려운 문제 추가
    for (Word word : hardWords) {
      quiz.add(generateQuestion(word, hardWords));
    }

    return quiz;
  }

  /**
   * 선지 생성 함수
   */
  private Question generateQuestion(Word correctWord, List<Word> wordList) {
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

    return new Question(correctWord.getKorean(), choices, correctWord.getEnglish());
  }


  /**
   * 채점 및 평가
   */
  public int gradeQuiz(List<Answer> answers, String apiKey) {
    // API KEY 유효성 검사
    if (apiKey == null || !apiKey.equals(xApiKey)) {
      throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
    }

    int score = 0;

    for (Answer answer : answers) {
      if (answer.getUserAnswer().equals(answer.getCorrectAnswer())) {
        score += 5;
      }
    }

    return score;
  }

  // 평가 등급 계산
  public String calculateGrade(int score, String apiKey) {
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
