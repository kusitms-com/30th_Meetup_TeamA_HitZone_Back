package kusitms.backend.stadium.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stadium")
public class StadiumEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    public StadiumEntity(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static StadiumEntity toEntity(
            Long id,
            String name
    ) {
        return new StadiumEntity(id, name);
    }
}
