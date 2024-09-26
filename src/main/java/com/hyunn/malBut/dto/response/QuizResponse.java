package com.hyunn.malBut.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class QuizResponse {
  private String korean;

  private List<String> choices;

  private String correctAnswer;

  public QuizResponse(String korean, List<String> choices, String correctAnswer) {
    this.korean = korean;
    this.choices = choices;
    this.correctAnswer = correctAnswer;
  }

  public static QuizResponse create(String korean, List<String> choices, String correctAnswer) {
    return new QuizResponse(korean, choices, correctAnswer);
  }

}
