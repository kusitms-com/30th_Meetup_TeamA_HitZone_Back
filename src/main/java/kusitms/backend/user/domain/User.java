package kusitms.backend.user.domain;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


}
