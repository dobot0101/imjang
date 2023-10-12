package com.dobot.imjang.domain.common.exception;

public class CustomException extends RuntimeException {
  private ErrorCode errorCode;

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  private String message;

  public String getMessage() {
    return message;
  }

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.message = errorCode.getMessage();
  }

  public CustomException(ErrorCode errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
  }
}
