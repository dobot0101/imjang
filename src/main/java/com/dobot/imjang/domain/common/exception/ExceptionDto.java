package com.dobot.imjang.domain.common.exception;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExceptionDto {
  private String message;
  private LocalDateTime timestamp;
  private Object errorData;
  private ErrorCode errorCode;

  @Builder
  public ExceptionDto(String message, ErrorCode errorCode, LocalDateTime timestamp, Object errorData) {
    this.message = message;
    this.errorCode = errorCode;
    this.timestamp = timestamp;
    this.errorData = errorData;
  }
}