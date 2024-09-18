package com.dobot.imjang.domain.common.exception;

public class ValidationError extends RuntimeException {

  private ErrorCode errorCode;

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  private String message;

  public String getMessage() {
    return message;
  }

  public ValidationError(String message) {
    this.errorCode = ErrorCode.BAD_REQUEST;
    this.message = message;
  }

  public ValidationError(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.message = errorCode.getMessage();
  }

  public ValidationError(ErrorCode errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
}
