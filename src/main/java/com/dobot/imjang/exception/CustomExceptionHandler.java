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
    ErrorResponse errorResponse = createErrorResponse(ex, HttpStatus.BAD_REQUEST);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    ErrorResponse errorResponse = this.createErrorResponse(ex, HttpStatus.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
    ErrorResponse errorResponse = this.createErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  private ErrorResponse createErrorResponse(Exception ex, HttpStatus httpStatus) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setStatusCode(httpStatus.value());
    errorResponse.setTimestamp(LocalDateTime.now());
    return errorResponse;
  }
}
