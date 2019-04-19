package com.tengnat.assistant.exception;

public class PermissionException extends RuntimeException {

    private static final long serialVersionUID = -8952764703543119728L;

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
