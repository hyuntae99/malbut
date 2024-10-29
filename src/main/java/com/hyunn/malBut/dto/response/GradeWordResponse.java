package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GradeWordResponse {

    @Schema(description = "점수", example = "85", required = true)
    private int score;

    @Schema(description = "등급", example = "A", required = true)
    private String grade;

    public GradeWordResponse(int score, String grade) {
        this.score = score;
        this.grade = grade;
    }

    public static GradeWordResponse create(int score, String grade) {
        return new GradeWordResponse(score, grade);
    }

}
