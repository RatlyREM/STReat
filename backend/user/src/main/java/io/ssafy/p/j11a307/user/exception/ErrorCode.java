package io.ssafy.p.j11a307.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 유저가 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "로그인 기간이 만료되었습니다. 다시 로그인 해주세요.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}