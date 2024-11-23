package kusitms.backend.result.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "user_id", nullable = false)
    private Long userId; // User를 ID로 간접 참조

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId; // Stadium을 ID로 간접 참조

    @Column(nullable = false)
    private String preference;

    @OneToOne(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zone> zones = new ArrayList<>();

    @Builder
    public Result(Long userId, Long stadiumId, String preference) {
        this.userId = userId;
        this.stadiumId = stadiumId;
        this.preference = preference;
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
        profile.assignToResult(this);
    }

    public void addZone(Zone zone){
        this.zones.add(zone);
        zone.assignToResult(this);
    }
}