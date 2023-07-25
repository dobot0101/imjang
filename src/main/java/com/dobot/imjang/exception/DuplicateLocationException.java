package com.dobot.imjang.exception;

// Custom 예외 클래스 정의
public class DuplicateLocationException extends RuntimeException {
  public DuplicateLocationException(String latitude, String longitude) {
    super("해당 위도, 경도에 건물이 존재합니다. [" + latitude + ", " + longitude + "]");
  }
}