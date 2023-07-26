package com.dobot.imjang.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(DuplicateLocationException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateLocationException(DuplicateLocationException ex) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
    errorResponse.setTimestamp(LocalDateTime.now());

    return ResponseEntity.badRequest().body(errorResponse);
  }
}
