package kusitms.backend.user.dto.response;

import kusitms.backend.user.domain.entity.User;

public record UserInfoResponseDto (
        String nickName,
        String email
) {
    public static UserInfoResponseDto from(User user) {
        return new UserInfoResponseDto(user.getNickName(), user.getEmail());
    }
}
