package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class PronunciationResponse {

  private int score;

  private String improvements;

  public PronunciationResponse(int score, String improvements) {
    this.score = score;
    this.improvements = improvements;
  }

  public static PronunciationResponse create(int score, String improvements) {
    return new PronunciationResponse(score, improvements);
  }

}