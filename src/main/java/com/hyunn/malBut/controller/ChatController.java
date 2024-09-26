package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.request.ChatRequest;
import com.hyunn.malBut.dto.response.ChatResponse;
import com.hyunn.malBut.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  @PostMapping
  public ResponseEntity<ChatResponse> chat(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestBody ChatRequest chatRequest) {
    String response = chatService.processChat(chatRequest.getSessionId(), chatRequest.getMessage(),
        apiKey);
    ChatResponse chatResponse = ChatResponse.create(chatRequest.getSessionId(), response);
    return ResponseEntity.ok(chatResponse);
  }
}
