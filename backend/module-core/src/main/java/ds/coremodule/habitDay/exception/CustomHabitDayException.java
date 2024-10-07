package ds.coremodule.habitDay.exception;

import ds.coremodule.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomHabitDayException extends RuntimeException {

    private final HttpStatus status;
    private final int code;

    public CustomHabitDayException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
    }
}