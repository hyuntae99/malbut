package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PronunciationResponse {

    @Schema(description = "평가 점수", example = "85", required = true)
    private int score;

    @Schema(description = "피드백 메시지", example = "Your pronunciation is very good!", required = true)
    private String improvements;

    public PronunciationResponse(int score, String improvements) {
        this.score = score;
        this.improvements = improvements;
    }

    public static PronunciationResponse create(int score, String improvements) {
        return new PronunciationResponse(score, improvements);
    }

}