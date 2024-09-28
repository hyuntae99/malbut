package com.hyunn.malBut.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluateProverbRequest {

  private String question;

  private String userAnswer;

  private String correctAnswer;

  public EvaluateProverbRequest(String question, String userAnswer, String correctAnswer) {
    this.question = question;
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public static EvaluateProverbRequest create(String question, String userAnswer, String correctAnswer) {
    return new EvaluateProverbRequest(question, userAnswer, correctAnswer);
  }

}