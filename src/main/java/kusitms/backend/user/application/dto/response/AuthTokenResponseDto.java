package kusitms.backend.user.application.dto.response;

public record AuthTokenResponseDto(
        String accessToken,
        String refreshToken,
        long accessTokenExpiration,
        long refreshTokenExpiration
) {
}
