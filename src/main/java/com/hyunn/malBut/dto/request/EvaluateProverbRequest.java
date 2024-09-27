package com.hyunn.malBut.dto.request;

import lombok.Getter;

@Getter
public class EvaluateProverbRequest {

  private String userAnswer;

  private String correctAnswer;

  public EvaluateProverbRequest(String userAnswer, String correctAnswer) {
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public static EvaluateProverbRequest create(String userAnswer, String correctAnswer) {
    return new EvaluateProverbRequest(userAnswer, correctAnswer);
  }

}