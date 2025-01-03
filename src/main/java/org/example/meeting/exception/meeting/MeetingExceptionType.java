package org.example.meeting.exception.meeting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.global.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum MeetingExceptionType implements ExceptionType {

    NOT_FOUND_MEETING(8000, "화상통화를 찾을 수 없습니다."),
    NOT_CREATE_MEETING(8010, "화상통화를 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
