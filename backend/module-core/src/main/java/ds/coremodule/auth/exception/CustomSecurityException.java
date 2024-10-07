package ds.coremodule.auth.exception;

import org.springframework.http.HttpStatus;

import ds.coremodule.exception.ErrorCode;
import lombok.Getter;


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
