package com.hyunn.malBut.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluateProverbRequest {

    @Schema(description = "질문", example = "___도 제 말하면 온다", required = true)
    private String question;

    @Schema(description = "유저 응답", example = "강아지도 제 말하면 온다", required = true)
    private String userAnswer;

    @Schema(description = "정답", example = "호랑이도 제 말하면 온다", required = true)
    private String correctAnswer;

    public EvaluateProverbRequest(String question, String userAnswer, String correctAnswer) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }

    public static EvaluateProverbRequest create(String question, String userAnswer, String correctAnswer) {
        return new EvaluateProverbRequest(question, userAnswer, correctAnswer);
    }

}