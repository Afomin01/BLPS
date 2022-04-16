package com.example.blps.controller.advice;

import com.example.blps.dto.response.ErrorResponse;
import com.example.blps.exception.NotFoundException;
import com.example.blps.exception.GeneralValidationException;
import com.example.blps.exception.GeneralCreationException;
import com.example.blps.exception.UserCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@ControllerAdvice
@Slf4j
public class MainControllerAdvice {
    @ExceptionHandler({GeneralValidationException.class, GeneralCreationException.class,
            UserCreationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleCreationExceptions(final RuntimeException e) {
        log.warn(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                Instant.now()
        );
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST).
                body(errorResponse);
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(final RuntimeException e) {
        log.warn(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                Instant.now()
        );
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                body(errorResponse);
    }
}
