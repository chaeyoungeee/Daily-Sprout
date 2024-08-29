package limchaeyoung.dailySprout.habitDay.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
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