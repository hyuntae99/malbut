package com.hyunn.malBut.dto.quiz;

import java.util.List;
import lombok.Getter;

@Getter
public class Question {
  private String korean;
  private List<String> choices;
  private String correctAnswer;

  public Question(String korean, List<String> choices, String correctAnswer) {
    this.korean = korean;
    this.choices = choices;
    this.correctAnswer = correctAnswer;
  }

}
