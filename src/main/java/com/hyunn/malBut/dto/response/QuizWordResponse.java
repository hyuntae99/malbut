package com.hyunn.malBut.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class QuizWordResponse {

  private String korean;

  private List<String> choices;

  private String correctAnswer;

  public QuizWordResponse(String korean, List<String> choices, String correctAnswer) {
    this.korean = korean;
    this.choices = choices;
    this.correctAnswer = correctAnswer;
  }

  public static QuizWordResponse create(String korean, List<String> choices, String correctAnswer) {
    return new QuizWordResponse(korean, choices, correctAnswer);
  }

}
