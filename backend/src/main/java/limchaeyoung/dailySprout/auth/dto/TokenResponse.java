package limchaeyoung.dailySprout.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}