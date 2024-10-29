package com.hyunn.malBut.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatRequest {

    @Schema(description = "세션 ID", example = "1234", required = true)
    private String sessionId;

    @Schema(description = "사용자의 메시지", example = "안녕, 나는 오늘 김치찌개를 먹었어.", required = true)
    private String message;

    public ChatRequest(String sessionId, String message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    public static ChatRequest create(String sessionId, String message) {
        return new ChatRequest(sessionId, message);
    }

}