package com.dobot.imjang.domain.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  // 컨트롤러 계층에서 @valid를 사용해서 처리하는 dto validation 에러 핸들러
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionDto> dtoValidation(final MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ExceptionDto exceptionDto = ExceptionDto.builder().message("Invalid Dto!")
        .timestamp(LocalDateTime.now()).errorData(errors).build();

    return ResponseEntity.status(exceptionDto.getErrorCode().getStatus())
        .body(exceptionDto);
  }

  // 서비스 계층에서 발생하는 커스텀 에러 핸들러
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ExceptionDto> handleCustomException(CustomException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder().message(e.getMessage()).errorCode(e.getErrorCode())
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.status(e.getErrorCode().getStatus()).body(exceptionDto);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ExceptionDto> handleCustomException(BadCredentialsException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder().message("인증에 실패했습니다.")
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDto);
  }

  // 그 외의 서버에서 발생하는 에러 핸들러
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionDto> handleRuntimException(RuntimeException e) {
    log.error(e.getMessage(), e);
    ExceptionDto exceptionDto = ExceptionDto.builder().message("서버에서 오류가 발생했습니다.").timestamp(LocalDateTime.now())
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDto);
  }
}
