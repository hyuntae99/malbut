package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class GradeResponse {

  private int score;

  private String grade;

  public GradeResponse(int score, String grade) {
    this.score = score;
    this.grade = grade;
  }

  public static GradeResponse create(int score, String grade) {
    return new GradeResponse(score, grade);
  }

}
