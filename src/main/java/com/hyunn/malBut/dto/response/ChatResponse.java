package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class ChatResponse {

  private String sessionId;

  private String reply;

  public ChatResponse(String sessionId, String reply) {
    this.sessionId = sessionId;
    this.reply = reply;
  }

  public static ChatResponse create(String sessionId, String reply) {
    return new ChatResponse(sessionId, reply);
  }

}