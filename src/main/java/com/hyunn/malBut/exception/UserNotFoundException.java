package com.hyunn.malBut.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public ErrorStatus toErrorCode() {
        return ErrorStatus.USER_NOT_FOUND_EXCEPTION;
    }
}
