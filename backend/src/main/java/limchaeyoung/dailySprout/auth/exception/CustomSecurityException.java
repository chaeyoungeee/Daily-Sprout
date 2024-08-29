package limchaeyoung.dailySprout.auth.exception;

import limchaeyoung.dailySprout.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CustomSecurityException extends RuntimeException {

	private final HttpStatus status;
	private final int code;

	public CustomSecurityException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.status = errorCode.getHttpStatus();
		this.code = errorCode.getCode();
	}
}
