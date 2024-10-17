package kusitms.backend.stadium.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.stadium.domain.enums.ProfileStatusType;
import kusitms.backend.user.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Result extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String preference;

    @Enumerated(EnumType.STRING)
    private ProfileStatusType profile;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RecommendZone> zones = new ArrayList<>();

}
