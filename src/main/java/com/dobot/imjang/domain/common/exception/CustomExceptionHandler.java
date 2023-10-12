package com.dobot.imjang.domain.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ExceptionDto> handleCustomException(CustomException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder().message(e.getMessage()).errorCode(e.getErrorCode())
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.status(e.getErrorCode().getStatus()).body(exceptionDto);
  }
}
