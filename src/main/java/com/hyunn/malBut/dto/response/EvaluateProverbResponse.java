package com.hyunn.malBut.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EvaluateProverbResponse {

  @JsonProperty("correctSentence")
  private String correctSentence;

  @JsonProperty("userSentence")
  private String userSentence;

  private int score;

  private String evaluate;

  public EvaluateProverbResponse(String correctSentence, String userSentence, int score, String evaluate) {
    this.correctSentence = correctSentence;
    this.userSentence = userSentence;
    this.score = score;
    this.evaluate = evaluate;
  }

  public static EvaluateProverbResponse create(String correctSentence, String userSentence, int score, String evaluate) {
    return new EvaluateProverbResponse(correctSentence, userSentence, score, evaluate);
  }

}
