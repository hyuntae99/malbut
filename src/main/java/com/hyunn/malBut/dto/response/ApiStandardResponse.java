package com.hyunn.malBut.dto.response;

import lombok.Getter;

@Getter
public class ApiStandardResponse<T> {

  private final String code;

  private final String msg;

  private final T data;

  private ApiStandardResponse(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> ApiStandardResponse<T> success(T data) {
    return new ApiStandardResponse<>("00", "success", data);
  }

  public static <T extends ErrorResponse> ApiStandardResponse<T> fail(T data) {
    return new ApiStandardResponse<>(data.getStatus().toString(), "fail", data);
  }
}
