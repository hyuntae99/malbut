package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class GradeWordResponse {

  private int score;

  private String grade;

  public GradeWordResponse(int score, String grade) {
    this.score = score;
    this.grade = grade;
  }

  public static GradeWordResponse create(int score, String grade) {
    return new GradeWordResponse(score, grade);
  }

}
