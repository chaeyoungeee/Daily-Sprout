package ds.coremodule.user.exception;

import ds.coremodule.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomUserException extends RuntimeException {

    private final HttpStatus status;
    private final int code;

    public CustomUserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
    }
}