package com.hyunn.malBut.dto.request;

import lombok.Getter;

@Getter
public class ChatRequest {

  private String sessionId;

  private String message;

  public ChatRequest(String sessionId, String message) {
    this.sessionId = sessionId;
    this.message = message;
  }

  public static ChatRequest create(String sessionId, String message) {
    return new ChatRequest(sessionId, message);
  }

}