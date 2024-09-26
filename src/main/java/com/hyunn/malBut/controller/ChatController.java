package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.ChatRequest;
import com.hyunn.malBut.service.ChatService;
import java.util.HashMap;
import java.util.Map;
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
  public ResponseEntity<Map<String, String>> chat(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestBody ChatRequest chatRequest) {
    String response = chatService.processChat(chatRequest.getSessionId(), chatRequest.getMessage(), apiKey);
    Map<String, String> result = new HashMap<>();
    result.put("reply", response);
    return ResponseEntity.ok(result);
  }
}
