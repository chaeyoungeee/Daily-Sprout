package limchaeyoung.daily_sprout.auth.exception;

import limchaeyoung.daily_sprout.common.exception.ErrorCode;
import lombok.Getter;


@Getter
public class CustomSecurityException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomSecurityException(final ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
