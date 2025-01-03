package org.example.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionType {

    NOT_FOUND_ATTENDEE(6000, "유저를 찾을 수 없습니다."),
    NOT_CREATE_ATTENDEE(6010, "유저를 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
