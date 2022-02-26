package com.example.blps.exception;

public class QuestionValidationException extends RuntimeException {
    public QuestionValidationException() {
        super();
    }

    public QuestionValidationException(String message) {
        super(message);
    }

    public QuestionValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionValidationException(Throwable cause) {
        super(cause);
    }

    protected QuestionValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
