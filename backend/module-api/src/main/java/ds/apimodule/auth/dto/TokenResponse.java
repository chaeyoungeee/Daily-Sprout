package ds.apimodule.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}