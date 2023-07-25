package com.dobot.imjang.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DuplicateLocationException.class)
  public ResponseEntity<String> handleDuplicateLocationException(DuplicateLocationException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
