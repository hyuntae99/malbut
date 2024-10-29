package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.response.ApiStandardResponse;
import com.hyunn.malBut.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "유저에게 인증 메시지 보내기",
            description = "사용자 이메일로 인증 코드를 전송합니다.")
    @PostMapping("/code")
    public ResponseEntity<ApiStandardResponse<String>> sendAuthentication(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @Parameter(description = "유저의 이메일 주소", required = true)
            @RequestParam("email") String email) throws IOException {

        String message = userService.sendAuthentication(email, apiKey);
        System.out.println(message);
        return ResponseEntity.ok(ApiStandardResponse.success("인증 코드가 전송되었습니다."));
    }

    @Operation(
            summary = "인증 코드 확인",
            description = "사용자가 입력한 인증 코드가 맞는지 확인합니다.")
    @PostMapping("/authentication")
    public ResponseEntity<ApiStandardResponse<String>> authentication(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @Parameter(description = "유저의 이메일 주소", required = true)
            @RequestParam("email") String email,

            @Parameter(description = "인증 코드", required = true)
            @RequestParam("code") String code) {

        String message = userService.authentication(email, code, apiKey);
        return ResponseEntity.ok(ApiStandardResponse.success(message));
    }

    @Operation(
            summary = "이메일 등록",
            description = "사용자의 이메일을 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiStandardResponse<String>> registerEmail(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @Parameter(description = "등록할 이메일 주소", required = true)
            @RequestParam("email") String email) {

        String message = userService.registerEmail(email, apiKey);
        return ResponseEntity.ok(ApiStandardResponse.success(message));
    }

    @Operation(
            summary = "유저 삭제",
            description = "사용자의 이메일을 기반으로 유저를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<ApiStandardResponse<String>> deleteUser(
            @Parameter(description = "API 키", required = false)
            @RequestHeader(value = "x-api-key", required = false) String apiKey,

            @Parameter(description = "삭제할 유저의 이메일 주소", required = true)
            @RequestParam("email") String email) {

        String message = userService.deleteUser(email, apiKey);
        return ResponseEntity.ok(ApiStandardResponse.success(message));
    }
}
