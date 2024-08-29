package limchaeyoung.dailySprout.habit.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
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