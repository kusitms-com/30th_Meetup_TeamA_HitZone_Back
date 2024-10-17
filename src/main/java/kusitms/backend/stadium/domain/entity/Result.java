package kusitms.backend.stadium.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.stadium.domain.enums.ProfileStatusType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Column(nullable = false)
    private String preference;

    @Enumerated(EnumType.STRING)
    private ProfileStatusType profile;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RecommendedZone> zones = new ArrayList<>();

}
