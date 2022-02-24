package com.example.blps.controller.advice;

import com.example.blps.exception.QuestionValidationException;
import com.example.blps.exception.TagCreationException;
import com.example.blps.exception.UserCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class MainControllerAdvice {
    @ExceptionHandler({QuestionValidationException.class, TagCreationException.class,
            UserCreationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleGeneralPermissionDeniedException(final RuntimeException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
