package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class QuizProverbResponse {

  @Schema(description = "속담의 영어 해석본", example = "It's a bad timing.", required = true)
  private String english;

  @Schema(description = "정답", example = "가는 날이 장날이다", required = true)
  private String correctAnswer;

  @Schema(description = "문제", example = "가는 날이 __이다", required = true)
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
