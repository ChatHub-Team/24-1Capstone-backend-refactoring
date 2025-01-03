package org.example.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.global.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum UserExceptionType implements ExceptionType {

    NOT_FOUND_USER(6000, "유저를 찾을 수 없습니다."),
    NOT_CREATE_USER(6010, "유저를 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
