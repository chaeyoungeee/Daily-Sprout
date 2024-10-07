package ds.apimodule.auth.util.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import ds.apimodule.auth.dto.HttpResponse;
import ds.commonmodule.response.StandardResponse;
import ds.coremodule.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증(Authentication) 예외가 발생했을 때 예외 처리 -> 인증되지 않은 사용자가 접근하려고 하는 경우
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException)
		throws IOException {
		HttpStatus httpStatus;
		StandardResponse<String> errorResponse;

		log.warn("AuthenticationException", authException);

		HttpResponse.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED_ACCESS.getErrorResponse());
	}
}
