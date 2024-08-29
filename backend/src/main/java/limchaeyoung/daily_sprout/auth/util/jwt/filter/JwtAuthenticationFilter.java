package limchaeyoung.daily_sprout.auth.util.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import limchaeyoung.daily_sprout.auth.domain.CustomUserDetails;
import limchaeyoung.daily_sprout.auth.exception.CustomSecurityException;
import limchaeyoung.daily_sprout.auth.util.jwt.JwtProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static limchaeyoung.daily_sprout.common.exception.ErrorCode.EXPIRED_TOKEN;

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
			throw new CustomSecurityException(EXPIRED_TOKEN);
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
		Authentication authentication = new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());

		// authentication 객체를 security context holder에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
