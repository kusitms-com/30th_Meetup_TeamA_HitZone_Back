package kusitms.backend.user.application.dto.response;

public record TokenResponseDto(
        String accessToken,
        String refreshToken,
        long accessTokenExpiration,
        long refreshTokenExpiration
) {
}
