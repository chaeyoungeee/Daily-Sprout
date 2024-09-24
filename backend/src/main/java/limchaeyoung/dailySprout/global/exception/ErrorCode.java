package limchaeyoung.dailySprout.global.exception;
import limchaeyoung.dailySprout.common.response.StandardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    public StandardResponse<Void> getErrorResponse() {
        return StandardResponse.failure(code, message);
    }
}
