package com.hyunn.malBut.exception;

public enum ErrorStatus {
  // 400 BAD_REQUEST 잘못된 요청
  // 401 Unauthorized 인증되지 않음
  // 403 Forbidden 권한 없음
  // 404 NOT_FOUND 잘못된 리소스 접근
  // 405 METHOD_NOT_ALLOWED 지원되지 않는 HTTP 요청 메서드
  // 500 INTERNAL SERVER ERROR 서버 오류
  INVALID_PARAMETER("01"),
  NEED_MORE_PARAMETER("02"),
  MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION("03"),
  USER_NOT_FOUND_EXCEPTION("04"),
  METHOD_NOT_ALLOWED_EXCEPTION("05"),
  DATABASE_ERROR("06"),
  INTERNAL_SERVER_ERROR("07"),
  VALIDATION_EXCEPTION("08"),
  INVALID_JSON_EXCEPTION("09"),
  API_NOT_FOUND_EXCEPTION("10"),
  UNAUTHORIZED_EXCEPTION("11");

  private final String code;

  ErrorStatus(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
