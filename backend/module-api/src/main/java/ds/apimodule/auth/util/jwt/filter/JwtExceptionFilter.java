package ds.apimodule.auth.util.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import ds.apimodule.auth.dto.HttpResponse;
import ds.commonmodule.response.StandardResponse;
import ds.coremodule.auth.exception.CustomSecurityException;
import ds.coremodule.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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
			StandardResponse<Void> errorResponse = StandardResponse.failure(
				e.getCode(),
				e.getMessage()
			);
			HttpResponse.sendErrorResponse(
				response,
				e.getStatus(),
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
