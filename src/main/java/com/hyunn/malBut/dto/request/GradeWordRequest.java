package com.hyunn.malBut.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GradeWordRequest {

  @Schema(description = "유저의 선택", example = "banana", required = true)
  private String userAnswer;

  @Schema(description = "정답", example = "Apple", required = true)
  private String correctAnswer;

  public GradeWordRequest(String userAnswer, String correctAnswer) {
    this.userAnswer = userAnswer;
    this.correctAnswer = correctAnswer;
  }

  public static GradeWordRequest create(String userAnswer, String correctAnswer) {
    return new GradeWordRequest(userAnswer, correctAnswer);
  }

}