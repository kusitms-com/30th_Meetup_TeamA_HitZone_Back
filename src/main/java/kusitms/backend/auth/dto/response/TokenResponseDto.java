package kusitms.backend.auth.dto.response;

public record TokenResponseDto(
        String accessToken,
        String refreshToken,
        long accessTokenExpiration,
        long refreshTokenExpiration
) {
}
