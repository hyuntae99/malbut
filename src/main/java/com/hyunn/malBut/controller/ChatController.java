package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.request.ChatRequest;
import com.hyunn.malBut.dto.response.ChatResponse;
import com.hyunn.malBut.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(
            summary = "채팅 메시지 처리",
            description = "사용자로부터 채팅 메시지를 받아 처리한 후 응답을 반환합니다.")
    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "채팅 요청 정보",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ChatRequest.class)
                    )
            )
            @RequestBody ChatRequest chatRequest) {

        String response = chatService.processChat(chatRequest.getSessionId(), chatRequest.getMessage(), apiKey);
        ChatResponse chatResponse = ChatResponse.create(chatRequest.getSessionId(), response);
        return ResponseEntity.ok(chatResponse);
    }
}
