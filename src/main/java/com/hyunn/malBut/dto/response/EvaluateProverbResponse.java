package com.hyunn.malBut.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EvaluateProverbResponse {

    @Schema(description = "정답", example = "호랑이도 제 말하면 온다", required = true)
    @JsonProperty("correctSentence")
    private String correctSentence;

    @Schema(description = "유저 응답", example = "강아지도 제 말하면 온다", required = true)
    @JsonProperty("userSentence")
    private String userSentence;

    @Schema(description = "점수", example = "85", required = true)
    private int score;

    @Schema(description = "평가", example = "Great!", required = true)
    private String evaluate;

    public EvaluateProverbResponse(String correctSentence, String userSentence, int score, String evaluate) {
        this.correctSentence = correctSentence;
        this.userSentence = userSentence;
        this.score = score;
        this.evaluate = evaluate;
    }

    public static EvaluateProverbResponse create(String correctSentence, String userSentence, int score,
                                                 String evaluate) {
        return new EvaluateProverbResponse(correctSentence, userSentence, score, evaluate);
    }

}
