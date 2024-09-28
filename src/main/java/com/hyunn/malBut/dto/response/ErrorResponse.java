package com.hyunn.malBut.dto.response;

import com.hyunn.malBut.exception.ErrorStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

  @Schema(description = "오류 코드", example = "03", required = true)
  private final ErrorStatus status;

  @Schema(description = "오류 메세지", example = "존재하지 않는 유저입니다.", required = true)
  private final String msg;

  public ErrorResponse(ErrorStatus status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  public static ErrorResponse create(ErrorStatus code, String msg) {
    return new ErrorResponse(code, msg);
  }
}

