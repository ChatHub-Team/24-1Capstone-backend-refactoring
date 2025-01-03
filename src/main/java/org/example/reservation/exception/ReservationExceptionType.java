package org.example.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.global.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum ReservationExceptionType implements ExceptionType {

    NOT_FOUND_RESERVATION(9000, "예약을 찾을 수 없습니다."),
    NOT_CREATE_RESERVATION(9010, "예약을 생성할 수 없습니다."),
    NOT_APPROVE_RESERVATION(9020, "예약을 승인할 수 없습니다."),
    NOT_REFUSE_RESERVATION(9030, "예약을 거절할 수 없습니다."),
    NOT_DELETE_RESERVATION(9040, "예약을 삭제할 수 없습니다."),
    NOT_AUTHORIZED_APPLY_USER(9050, "예약 신청자만 접근할 수 있습니다."),
    NOT_AUTHORIZED_RECEIVE_USER(9060, "예약 수신자만 접근할 수 있습니다.");

    private final int statusCode;
    private final String message;
}
