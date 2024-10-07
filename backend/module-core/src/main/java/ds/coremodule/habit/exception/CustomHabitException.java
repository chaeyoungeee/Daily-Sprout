package ds.coremodule.habit.exception;

import ds.coremodule.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomHabitException extends RuntimeException {

    private final HttpStatus status;
    private final int code;

    public CustomHabitException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
    }
}