package kusitms.backend.user.application.dto.response;

import kusitms.backend.user.domain.model.User;

public record UserInfoResponseDto (
        String nickname,
        String email
) {
    public static UserInfoResponseDto from(User user) {
        return new UserInfoResponseDto(
                user.getNickname(),
                user.getEmail()
        );
    }
}
