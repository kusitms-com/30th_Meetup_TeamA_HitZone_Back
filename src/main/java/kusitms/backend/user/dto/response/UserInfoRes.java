package kusitms.backend.user.dto.response;

import kusitms.backend.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfoRes {
    private String name;
    private String email;
    private String phoneNumber;

    public static UserInfoRes of(User user) {
        return UserInfoRes
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
