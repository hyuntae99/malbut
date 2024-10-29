package com.hyunn.malBut.exception;

import static com.hyunn.malBut.exception.ErrorStatus.INVALID_JSON_EXCEPTION;
import static com.hyunn.malBut.exception.ErrorStatus.INVALID_PARAMETER;
import static com.hyunn.malBut.exception.ErrorStatus.MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION;
import static com.hyunn.malBut.exception.ErrorStatus.NEED_MORE_PARAMETER;
import static com.hyunn.malBut.exception.ErrorStatus.VALIDATION_EXCEPTION;

import com.hyunn.malBut.dto.response.ApiStandardResponse;
import com.hyunn.malBut.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // x-api-key 헤더가 올바르지 않은 경우
    @ExceptionHandler(ApiKeyNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiStandardResponse<ErrorResponse> handleApiKeyNotValidException(
            ApiKeyNotValidException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
        return ApiStandardResponse.fail(errorResponse);
    }

    // 유저를 찾을 수 없는 경우
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiStandardResponse<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
        return ApiStandardResponse.fail(errorResponse);
    }

    // 인증 오류
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiStandardResponse<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
        return ApiStandardResponse.fail(errorResponse);
    }

    // API 응답이 올바르지 않은 경우
    @ExceptionHandler(ApiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiStandardResponse<ErrorResponse> handleApiNotFoundException(ApiNotFoundException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
        return ApiStandardResponse.fail(errorResponse);
    }

    // 파라미터가 올바르지 않은 경우 (validation에 걸린 경우)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiStandardResponse<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException e) {
        log.error("", e);

        // ConstraintViolationException에서 발생한 오류 메세지 추출
        String errorMessage = "";
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errorMessage = violation.getMessage();
            break; // 첫 번째 오류 메세지만 추출
        }

        final ErrorResponse errorResponse = ErrorResponse.create(INVALID_PARAMETER, errorMessage);
        return ApiStandardResponse.fail(errorResponse);
    }

    // 파라미터 값이 올바르지 않은 경우
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiStandardResponse<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(INVALID_PARAMETER,
                "올바르지 않은 파라미터 값입니다.");
        return ApiStandardResponse.fail(errorResponse);
    }

    // 파라미터 값이 부족한 경우
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiStandardResponse<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(NEED_MORE_PARAMETER,
                "파라미터가 부족합니다.");
        return ApiStandardResponse.fail(errorResponse);
    }

    // Validation 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiStandardResponse<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("", e);

        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // error 메세지 추출
        String errors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        final ErrorResponse errorResponse = ErrorResponse.create(VALIDATION_EXCEPTION, errors);
        return ApiStandardResponse.fail(errorResponse);
    }

    // JSON 형식 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiStandardResponse<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        ErrorResponse errorResponse = ErrorResponse.create(INVALID_JSON_EXCEPTION,
                "올바르지 않은 JSON 형식입니다.");
        return ApiStandardResponse.fail(errorResponse);
    }

    // 형식 오류
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiStandardResponse<ErrorResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e) {
        ErrorResponse errorResponse = ErrorResponse.create(MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION,
                "지원하지 않는 형식의 데이터 요청입니다.");
        return ApiStandardResponse.fail(errorResponse);
    }

    // 지원하지 않는 HTTP Method를 사용했을 경우
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiStandardResponse<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(
                ErrorStatus.METHOD_NOT_ALLOWED_EXCEPTION, "지원하지 않는 HTTP Method입니다.");
        return ApiStandardResponse.fail(errorResponse);
    }

    // 데이터 베이스 오류
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiStandardResponse<ErrorResponse> handleDataAccessException(DataAccessException e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.DATABASE_ERROR,
                "데이터베이스에 오류가 발생했습니다.");
        return ApiStandardResponse.fail(errorResponse);
    }

    // 내부 서버 오류
    @ExceptionHandler(InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiStandardResponse<ErrorResponse> handleInternalServerError(InternalServerError e) {
        log.error("", e);

        final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INTERNAL_SERVER_ERROR,
                "예상치 못한 오류가 발생했습니다. 관리자에게 문의해주세요.");
        return ApiStandardResponse.fail(errorResponse);
    }
}

