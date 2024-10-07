package ds.apimodule.auth.util.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import ds.apimodule.auth.domain.CustomUserDetails;
import ds.apimodule.auth.util.jwt.JwtProvider;
import ds.coremodule.auth.exception.CustomSecurityException;
import ds.coremodule.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		try {
			String accessToken = jwtProvider.resolveAccessToken(request);

			// header에 token이 존재하지 않는 경우
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// 정상 토큰인 경우 인증 처리
			authenticateAccessToken(accessToken);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			log.warn("AccessToken Expired");
			throw new CustomSecurityException(ErrorCode.EXPIRED_TOKEN);
		}
	}

	private void authenticateAccessToken(String accessToken) {
		CustomUserDetails userDetails = new CustomUserDetails(
			jwtProvider.getId(accessToken),
			jwtProvider.getEmail(accessToken),
			null,
			jwtProvider.getAuthority(accessToken)
		);

		// 인증용 객체 생성
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());

		// authentication 객체를 security context holder에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
