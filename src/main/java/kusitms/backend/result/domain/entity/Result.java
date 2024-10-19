package kusitms.backend.result.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.user.domain.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String preference;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<Zone> zones;

    @Builder
    public Result(Stadium stadium, User user, String preference) {
        this.stadium = stadium;
        this.user = user;
        this.preference = preference;
    }
}
