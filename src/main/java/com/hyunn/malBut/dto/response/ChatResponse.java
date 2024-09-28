package com.hyunn.malBut.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatResponse {

  @Schema(description = "세션 ID", example = "1234", required = true)
  private String sessionId;

  @Schema(description = "챗봇의 응답", example = "안녕하세요! 무엇을 도와드릴까요?", required = true)
  private String reply;


  public ChatResponse(String sessionId, String reply) {
    this.sessionId = sessionId;
    this.reply = reply;
  }

  public static ChatResponse create(String sessionId, String reply) {
    return new ChatResponse(sessionId, reply);
  }

}