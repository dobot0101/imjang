package com.dobot.imjang.domain.common.exceptions;

public enum ExceptionMessage {
    BUILDING_NOT_FOUND("빌딩이 존재하지 않습니다."),
    UNIT_NOT_FOUND("유닛이 존재하지 않습니다."),
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다."),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다."),;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}