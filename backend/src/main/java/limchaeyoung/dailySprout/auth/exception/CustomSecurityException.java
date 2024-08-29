package limchaeyoung.dailySprout.auth.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
import lombok.Getter;


@Getter
public class CustomSecurityException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomSecurityException(final ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
