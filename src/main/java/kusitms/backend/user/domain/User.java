package kusitms.backend.user.domain;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
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

    @Builder
    public User(ProviderStatusType provider, String providerId, String email, String name, String phoneNumber){
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}