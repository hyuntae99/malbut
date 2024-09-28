package com.hyunn.malBut.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PythonEvaluationResponse {

  private String sentence1; // 정답
  private String sentence2; // 유저 응답
  private double similarity; // 코사인 유사도
  private double delivery_score; // 전달 점수
  private int morph_penalty; // 형태소 분석에 의한 감점 점수
  private double final_score; // 최종 점수
}
