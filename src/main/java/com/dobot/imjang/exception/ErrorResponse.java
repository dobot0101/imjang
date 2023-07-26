package com.dobot.imjang.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
  private String message;
  private int statusCode;
  private LocalDateTime timestamp;
  private Object errorData;
}