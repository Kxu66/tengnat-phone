package com.tengnat.assistant.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -6546615259697768730L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
