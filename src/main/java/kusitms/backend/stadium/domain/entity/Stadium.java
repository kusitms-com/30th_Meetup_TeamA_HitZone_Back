package kusitms.backend.stadium.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    public Stadium(String name) {
        this.name = name;
    }
}
