package kusitms.backend.user.domain;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.*;


@Entity(name="users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User extends BaseTimeEntity {

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
    private String name;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    public static User to(String provider, String providerId, String email, String name, String phoneNumber) {
        return User.builder()
                .provider(ProviderStatusType.of(provider))
                .providerId(providerId)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }

}
