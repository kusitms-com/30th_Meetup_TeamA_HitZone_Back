package kusitms.backend.result.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "result")
public class ResultEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "stadium_id", nullable = false)
    private Long stadiumId;

    @Column(nullable = false)
    private String preference;

    @OneToOne(mappedBy = "resultEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileEntity profileEntity;

    @OneToMany(mappedBy = "resultEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneEntity> zoneEntities = new ArrayList<>();

    public ResultEntity(
            Long id,
            Long userId,
            Long stadiumId,
            String preference
    ) {
        this.id = id;
        this.userId = userId;
        this.stadiumId = stadiumId;
        this.preference = preference;
    }

    public static ResultEntity toEntity(
            Long id,
            Long userId,
            Long stadiumId,
            String preference
    ) {
        return new ResultEntity(id, userId, stadiumId, preference);
    }

    public void addProfileEntity(ProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
        profileEntity.assignToResultEntity(this);
    }

    public void addZoneEntity(ZoneEntity zoneEntity) {
        this.zoneEntities.add(zoneEntity);
        zoneEntity.assignToResultEntity(this);
    }
}
