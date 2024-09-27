package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class EvaluateProverbResponse {

  private int score;

  private String evaluate;

  public EvaluateProverbResponse(int score, String evaluate) {
    this.score = score;
    this.evaluate = evaluate;
  }

  public static EvaluateProverbResponse create(int score, String evaluate) {
    return new EvaluateProverbResponse(score, evaluate);
  }

}
