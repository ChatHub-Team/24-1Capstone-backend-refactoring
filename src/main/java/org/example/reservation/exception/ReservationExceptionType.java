package org.example.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.global.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum ReservationExceptionType implements ExceptionType {

    NOT_FOUND_MEETING(9000, "예약을 찾을 수 없습니다."),
    NOT_CREATE_MEETING(9010, "예약을 생성할 수 없습니다.");

    private final int statusCode;
    private final String message;
}
