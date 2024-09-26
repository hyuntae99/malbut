package com.hyunn.malBut.dto.request;

import lombok.Getter;

@Getter
public class GradeRequest {

  private String userAnswer;

  private String correctAnswer;

  public GradeRequest(String userAnswer, String correctAnswer) {
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public static GradeRequest create(String userAnswer, String correctAnswer) {
    return new GradeRequest(userAnswer, correctAnswer);
  }

}