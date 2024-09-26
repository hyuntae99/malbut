package com.hyunn.malBut.controller;

import com.hyunn.malBut.dto.ApiStandardResponse;
import com.hyunn.malBut.service.UserService;
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

  /**
   * 유저에게 인증 메세지 보내기
   */
  @PostMapping("/code")
  public ResponseEntity<ApiStandardResponse<String>> sendAuthentication(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("email") String email) throws IOException {
    String message = userService.sendAuthentication(email, apiKey);
    System.out.println(message);
    return ResponseEntity.ok(ApiStandardResponse.success("인증 코드가 전송되었습니다."));
  }

  /**
   * 인증 코드가 맞는지 확인하기
   */
  @PostMapping("/authentication")
  public ResponseEntity<ApiStandardResponse<String>> authentication(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("email") String email, @RequestParam("code") String code) {
    String message = userService.authentication(email, code, apiKey);
    return ResponseEntity.ok(ApiStandardResponse.success(message));
  }

  /**
   * 이메일 등록
   */
  @PostMapping()
  public ResponseEntity<ApiStandardResponse<String>> registerEmail(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("email") String email) {
    String message = userService.registerEmail(email, apiKey);
    return ResponseEntity.ok(ApiStandardResponse.success(message));
  }

  /**
   * 유저 삭제
   */
  @DeleteMapping()
  public ResponseEntity<ApiStandardResponse<String>> deleteUser(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("email") String email) {
    String message = userService.deleteUser(email, apiKey);
    return ResponseEntity.ok(ApiStandardResponse.success(message));
  }
}
