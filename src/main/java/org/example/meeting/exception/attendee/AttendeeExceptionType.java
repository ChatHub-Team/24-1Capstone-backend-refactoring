package org.example.meeting.exception.attendee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.global.exception.ExceptionType;

@AllArgsConstructor
@Getter
public enum AttendeeExceptionType implements ExceptionType {

    NOT_FOUND_ATTENDEE(7000, "참여자를 찾을 수 없습니다."),
    NOT_CREATE_ATTENDEE(7010, "참여자를 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
