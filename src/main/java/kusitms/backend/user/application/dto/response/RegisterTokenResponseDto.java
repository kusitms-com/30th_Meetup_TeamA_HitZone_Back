package kusitms.backend.user.application.dto.response;

public record RegisterTokenResponseDto(
        String registerToken,
        long registerTokenExpiration
) {
}
