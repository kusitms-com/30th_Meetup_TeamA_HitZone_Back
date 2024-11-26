package kusitms.backend.user.domain.model;

import kusitms.backend.user.domain.enums.ProviderStatusType;
import kusitms.backend.user.infra.jpa.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private Long id;
    private ProviderStatusType provider;
    private String providerId;
    private String email;
    private String nickname;
//    private String phoneNumber;

    public User(
            Long id,
            ProviderStatusType provider,
            String providerId,
            String email,
            String nickname
    ) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickname = nickname;
    }

    public static UserEntity toDomain(
            Long id,
            ProviderStatusType provider,
            String providerId,
            String email,
            String nickname
    ) {
        return new UserEntity(id, provider, providerId, email, nickname);
    }
}

