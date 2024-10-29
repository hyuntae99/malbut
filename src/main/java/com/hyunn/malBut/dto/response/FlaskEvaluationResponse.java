package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlaskEvaluationResponse {

    @Schema(description = "속담 (정답)")
    private String sentence1;

    @Schema(description = "유저의 응답")
    private String sentence2;

    @Schema(description = "코사인 유사도")
    private double similarity;

    @Schema(description = "전달 점수")
    private double delivery_score;

    @Schema(description = "형태소 분석에 의한 감점 점수")
    private int morph_penalty;

    @Schema(description = "최종 점수")
    private double final_score;
}
