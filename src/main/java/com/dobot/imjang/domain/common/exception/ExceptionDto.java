package com.dobot.imjang.domain.common.exception;

import java.time.LocalDateTime;

public class ExceptionDto {
  private String message;
  private ErrorCode errorCode;
  
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  private LocalDateTime timestamp;
  private Object errorData;

  private ExceptionDto(Builder builder) {
    this.message = builder.message;
    this.errorCode = builder.errorCode;
    this.timestamp = builder.timestamp;
    this.errorData = builder.errorData;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String message;
    private ErrorCode errorCode;
    private LocalDateTime timestamp;
    private Object errorData;

    public ExceptionDto build() {
      return new ExceptionDto(this);
    }

    public Builder message(String message) {
      this.message = message;
      return this;
    }

    public Builder errorCode(ErrorCode errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public Builder timestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder errorData(Object errorData) {
      this.errorData = errorData;
      return this;
    }
  }
}