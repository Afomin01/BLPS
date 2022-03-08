package com.example.blps.exception;

public class GeneralCreationException extends RuntimeException {
    public GeneralCreationException() {
        super();
    }

    public GeneralCreationException(String message) {
        super(message);
    }

    public GeneralCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralCreationException(Throwable cause) {
        super(cause);
    }

    protected GeneralCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
