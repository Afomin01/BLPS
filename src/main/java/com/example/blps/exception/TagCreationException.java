package com.example.blps.exception;

public class TagCreationException extends RuntimeException {
    public TagCreationException() {
        super();
    }

    public TagCreationException(String message) {
        super(message);
    }

    public TagCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagCreationException(Throwable cause) {
        super(cause);
    }

    protected TagCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
