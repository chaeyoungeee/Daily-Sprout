package limchaeyoung.dailySprout.common.exception;

import limchaeyoung.dailySprout.common.response.StandardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, 2001, "사용자가 존재하지 않습니다."),

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, 4001, "사용자 인증이 필요합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, 4002, "해당 요청에 대한 접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 4003, "토큰 형식이 올바르지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 4004, "토큰이 만료되었습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, 4005, "토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, 4006, "리프레시 토큰이 유효하지만 저장된 토큰과 일치하지 않습니다."),
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, 4007, "토큰이 위조되었습니다."),
    KAKAO_CLIENT_ERROR(HttpStatus.BAD_REQUEST,  4021, "잘못된 요청으로 카카오 토큰 발급에 실패했습니다."),
    KAKAO_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  4022, "카카오 서버 에러로 인해 토큰 발급에 실패했습니다."),
    INTERNAL_AUTH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  4031, "인증 처리 중 서버 에러가 발생했습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 에러가 발생했습니다."),

    DAY_NOT_EXIST(HttpStatus.BAD_REQUEST, 6001, "존재하지 않는 요일입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    public StandardResponse<Void> getErrorResponse() {
        return StandardResponse.failure(code, message);
    }
}
