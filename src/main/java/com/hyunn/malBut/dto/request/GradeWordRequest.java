package com.hyunn.malBut.dto.request;

import lombok.Getter;

@Getter
public class GradeWordRequest {

  private String userAnswer;

  private String correctAnswer;

  public GradeWordRequest(String userAnswer, String correctAnswer) {
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public static GradeWordRequest create(String userAnswer, String correctAnswer) {
    return new GradeWordRequest(userAnswer, correctAnswer);
  }

}