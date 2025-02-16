package com.mypolls.exception;

public class NicknameAlreadyInUseException extends RuntimeException {
    public NicknameAlreadyInUseException(String message) {
        super(message);
    }
}