package com.github.enma11235.surveysystemapi.exception;

public class NicknameAlreadyInUseException extends RuntimeException {
    public NicknameAlreadyInUseException(String message) {
        super(message);
    }
}