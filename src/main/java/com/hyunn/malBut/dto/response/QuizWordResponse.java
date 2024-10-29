package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
public class QuizWordResponse {

    @Schema(description = "질문 내용", example = "사과", required = true)
    private String korean;

    @Schema(description = "선택지 목록", example = "[\"Apple\", \"Banana\", \"Cherry\", \"Date\"]", required = true)
    private List<String> choices;

    @Schema(description = "정답", example = "apple", required = true)
    private String correctAnswer;

    public QuizWordResponse(String korean, List<String> choices, String correctAnswer) {
        this.korean = korean;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public static QuizWordResponse create(String korean, List<String> choices, String correctAnswer) {
        return new QuizWordResponse(korean, choices, correctAnswer);
    }

}
