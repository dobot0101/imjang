package com.dobot.imjang.domain.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 컨트롤러의 DTO Validation Exception 핸들러
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionDto> dtoValidation(final MethodArgumentNotValidException e) {
    FieldError error = (FieldError) e.getBindingResult().getAllErrors().get(0);
    ExceptionDto exceptionDto = ExceptionDto.builder().message(error.getDefaultMessage())
        .errorCode(ErrorCode.BAD_REQUEST)
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.badRequest().body(exceptionDto);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ExceptionDto> handleValidationException(BadCredentialsException e) {
    log.error(e.getMessage(), e);
    ExceptionDto exceptionDto = ExceptionDto.builder().message("아이디 또는 비밀번호가 잘못되었습니다.")
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDto);
  }

  /**
   * 서비스 계층에서 발생하는 커스텀 에러 핸들러
   */
  @ExceptionHandler(ValidationError.class)
  public ResponseEntity<ExceptionDto> handleValidationException(ValidationError e) {
    ExceptionDto exceptionDto = ExceptionDto.builder().message(e.getMessage())
        .errorCode(e.getErrorCode())
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.status(e.getErrorCode().getStatus()).body(exceptionDto);
  }

  /**
   * 인증 실패 에러 핸들러
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ExceptionDto> handleAuthenticationException(
      AuthenticationException e) {
    ExceptionDto exceptionDto = ExceptionDto.builder().message(e.getMessage())
        .timestamp(LocalDateTime.now()).build();

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionDto);
  }

  /**
   * 그 외의 서버에서 발생하는 에러 핸들러
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDto> handleRuntimeException(RuntimeException e) {
    log.error(e.getMessage(), e);
    ExceptionDto exceptionDto = ExceptionDto.builder().message("서버에서 오류가 발생했습니다.")
        .timestamp(LocalDateTime.now())
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDto);
  }
}
