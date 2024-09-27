package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class QuizProverbResponse {

  private String english;

  private String correctAnswer;

  private String question;

  public QuizProverbResponse(String english, String correctAnswer, String question) {
    this.english = english;
    this.correctAnswer = correctAnswer;
    this.question = question;
  }

  public static QuizProverbResponse create(String english, String correctAnswer, String question) {
    return new QuizProverbResponse(english, correctAnswer, question);
  }

}
