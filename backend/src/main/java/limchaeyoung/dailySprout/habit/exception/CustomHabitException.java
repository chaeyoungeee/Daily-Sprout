package limchaeyoung.dailySprout.habit.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomHabitException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomHabitException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}