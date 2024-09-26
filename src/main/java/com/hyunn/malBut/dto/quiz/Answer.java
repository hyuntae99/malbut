package com.hyunn.malBut.dto.quiz;

import lombok.Getter;

@Getter
public class Answer {

  private String userAnswer;
  private String correctAnswer;

  public Answer(String userAnswer, String correctAnswer) {
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }
}