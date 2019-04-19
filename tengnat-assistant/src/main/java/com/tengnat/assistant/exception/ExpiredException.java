package com.tengnat.assistant.exception;


public class ExpiredException extends RuntimeException {

    private static final long serialVersionUID = -9195585967688402415L;

    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
