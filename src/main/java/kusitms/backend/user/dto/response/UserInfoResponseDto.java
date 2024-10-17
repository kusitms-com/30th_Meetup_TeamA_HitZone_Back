package kusitms.backend.user.dto.response;

import kusitms.backend.user.domain.User;

public record UserInfoResponseDto (
        String name,
        String email,
        String phoneNumber
){
    public static UserInfoResponseDto of(User user) {
        return new UserInfoResponseDto(user.getName(), user.getEmail(), user.getPhoneNumber());
    }
}
