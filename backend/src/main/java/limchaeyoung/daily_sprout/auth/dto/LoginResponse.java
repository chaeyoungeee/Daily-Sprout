package limchaeyoung.daily_sprout.auth.dto;

public record LoginResponse(
        String email,
        String nickname,
        String accessToken,
        String refreshToken
) {
}
