package org.example.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.global.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum UserExceptionType implements ExceptionType {

    NOT_FOUND_USER(5000, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_USERS(5001, "사용자 목록이 존재하지 않습니다."),
    NOT_CREATE_USER(5010, "사용자를 생성할 수 없습니다."),
    NOT_UPDATE_USER(5020, "사용자 정보를 수정할 수 없습니다."),
    NOT_DELETE_USER(5030, "사용자를 삭제할 수 없습니다."),
    GITHUB_API_ERROR(5040, "Github API 호출 중 오류가 발생했습니다.");

    private final int statusCode;
    private final String message;
}
