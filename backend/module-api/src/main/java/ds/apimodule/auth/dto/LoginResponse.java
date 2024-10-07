package ds.apimodule.auth.dto;

public record LoginResponse(
        String email,
        String nickname,
        String accessToken,
        String refreshToken
) {
}
