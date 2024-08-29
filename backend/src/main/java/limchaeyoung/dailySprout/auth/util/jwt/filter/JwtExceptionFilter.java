package limchaeyoung.dailySprout.auth.util.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import limchaeyoung.dailySprout.auth.dto.HttpResponse;
import limchaeyoung.dailySprout.auth.exception.CustomSecurityException;
import limchaeyoung.dailySprout.common.exception.ErrorCode;
import limchaeyoung.dailySprout.common.response.StandardResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain) throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (CustomSecurityException e) {
			log.warn("CustomSecurityException", e);
			ErrorCode errorCode = e.getErrorCode();
			StandardResponse<Void> errorResponse = StandardResponse.failure(
				errorCode.getCode(),
				errorCode.getMessage()
			);
			HttpResponse.sendErrorResponse(
				response,
				errorCode.getHttpStatus(),
				errorResponse
			);
		} catch (Exception e) {
			log.error("Exception", e);

			HttpResponse.sendErrorResponse(
				response,
				HttpStatus.INTERNAL_SERVER_ERROR,
				ErrorCode.INTERNAL_AUTH_ERROR.getErrorResponse()
			);
		}
	}
}
