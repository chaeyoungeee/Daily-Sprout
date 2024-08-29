package limchaeyoung.dailySprout.achievement.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomAchievementException extends RuntimeException {

    private final int code;
    private final String message;

    public CustomAchievementException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}