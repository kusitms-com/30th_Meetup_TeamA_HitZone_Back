package kusitms.backend.stadium.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.infra.converter.StringListConverter;
import kusitms.backend.stadium.domain.enums.Boundary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "entertainment")
public class EntertainmentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entertainment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private StadiumEntity stadiumEntity;

    @Column(nullable = false)
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Boundary boundary;

    @Column(nullable = false)
    private String name;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> explanations;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> tips;

    public EntertainmentEntity(
            Long id,
            String imgUrl,
            Boundary boundary,
            String name,
            List<String> explanations,
            List<String> tips
    ) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.boundary = boundary;
        this.name = name;
        this.explanations = explanations;
        this.tips = tips;
    }

    public static EntertainmentEntity toEntity(
            Long id,
            String imgUrl,
            Boundary boundary,
            String name,
            List<String> explanations,
            List<String> tips
    ) {
        return new EntertainmentEntity(id, imgUrl, boundary, name, explanations, tips);
    }

    public void assignToStadiumEntity(StadiumEntity stadiumEntity) {
        this.stadiumEntity = stadiumEntity;
    }
}
