package limchaeyoung.daily_sprout.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}