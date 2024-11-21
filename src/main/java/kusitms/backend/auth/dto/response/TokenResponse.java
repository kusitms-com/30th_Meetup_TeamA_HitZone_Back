package kusitms.backend.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        long accessTokenExpiration,
        long refreshTokenExpiration
) {
}
