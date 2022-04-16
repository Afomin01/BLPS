package com.example.blps.exception;

public class GeneralValidationException extends RuntimeException {
    public GeneralValidationException() {
        super();
    }

    public GeneralValidationException(String message) {
        super(message);
    }

    public GeneralValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralValidationException(Throwable cause) {
        super(cause);
    }

    protected GeneralValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
