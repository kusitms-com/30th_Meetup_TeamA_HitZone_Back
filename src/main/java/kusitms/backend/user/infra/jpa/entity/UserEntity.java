package kusitms.backend.user.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.user.domain.enums.ProviderStatusType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderStatusType provider;

    @Column(nullable = false, unique = true)
    private String providerId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

//    @Column(nullable = false, unique = true)
//    private String phoneNumber;

    public UserEntity(
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
