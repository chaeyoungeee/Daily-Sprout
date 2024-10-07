package ds.apimodule.auth.util.jwt;

import static ds.coremodule.exception.ErrorCode.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ds.apimodule.auth.dto.TokenResponse;
import ds.commonmodule.redis.util.RedisUtil;
import ds.coremodule.auth.exception.CustomSecurityException;
import ds.coremodule.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class JwtProvider {

    private final SecretKey secretKey;
    private final Long accessExpMs;
    private final Long refreshExpMs;
    private final RedisUtil redisUtil;

    public static final String AUTHORIZATION = "Authorization";

    public JwtProvider(
            @Value("${security.jwt.secret}") String secretKey,
            @Value("${security.jwt.token.access-expiration-time}") Long accessExpMs,
            @Value("${security.jwt.token.refresh-expiration-time}") Long refreshExpMs,
            RedisUtil redisUtil) {

        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessExpMs = accessExpMs;
        this.refreshExpMs = refreshExpMs;
        this.redisUtil = redisUtil;
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("Invalid or missing Authorization header");
            return null;
        }

        log.info("Token exists");
        return authorization.split(" ")[1];
    }

    public String issueJwtAccessToken(Long id, String email, String nickname) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(accessExpMs);

        return createToken(id, email, nickname, issuedAt, expiration);
    }

    public String issueJwtRefreshToken(Long id, String email, String nickname) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(refreshExpMs);

        String refreshToken = createToken(id, email, nickname, issuedAt, expiration);

        redisUtil.saveAsValue(
                "refresh_token_" + email,
                refreshToken,
                refreshExpMs,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

    private String createToken(Long id, String email, String nickname, Instant issuedAt, Instant expiration) {
        return Jwts.builder()
                .setSubject(id.toString())
                .claim("email", email)
                .claim("nickname", nickname)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public TokenResponse reissueToken(String refreshToken) {
        try {
            validateRefreshToken(refreshToken);

            // redis에서 기존 refreshToken 삭제
            String email = getEmail(refreshToken);
            Long id = getId(refreshToken);
            redisUtil.delete("refresh_token_" + email);

            String nickname = getNickname(refreshToken);

            return new TokenResponse(
                    issueJwtAccessToken(id, email, nickname),
                    issueJwtRefreshToken(id, email, nickname)
            );
        } catch (IllegalArgumentException e) {
            throw new CustomSecurityException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomSecurityException(EXPIRED_TOKEN);
        }
    }

    public void deleteToken(String refreshToken) {
        String email = getEmail(refreshToken);
        redisUtil.delete("refresh_token_" + email);
    }

    public void validateRefreshToken(String refreshToken) {
        String email = getEmail(refreshToken);
        String key = "refresh_token_" + email;

        // 저장된 refresh token인지 검증
        if (!redisUtil.hasKey(key)) {
            log.warn("Invalid refreshToken");
            throw new CustomSecurityException(TOKEN_NOT_FOUND);
        }

        // refresh token이 일치하는지 검증
        if (!redisUtil.get(key).equals(refreshToken)) {
            log.warn("Mismatch refreshToken");
            throw new CustomSecurityException(ErrorCode.REFRESH_TOKEN_MISMATCH);
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new CustomSecurityException(ErrorCode.INVALID_TOKEN);
        }
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public Long getId(String token) {
        return Long.parseLong(getSubject(token));
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public String getNickname(String token) {
        return getClaims(token).get("nickname", String.class);
    }

    public String getAuthority(String token) {
        return getClaims(token).get("auth", String.class);
    }
}
