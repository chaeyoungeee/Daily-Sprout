package limchaeyoung.dailySprout.user.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomUserException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomUserException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}