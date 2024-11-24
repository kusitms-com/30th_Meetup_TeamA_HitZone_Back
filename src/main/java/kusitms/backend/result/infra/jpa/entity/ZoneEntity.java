package kusitms.backend.result.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.domain.value.ReferencesGroup;
import kusitms.backend.result.infra.converter.ReferencesGroupConverter;
import kusitms.backend.result.infra.converter.StringListConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recommend_zone")
public class ZoneEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zone_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private ResultEntity resultEntity;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> explanations;

    @Column(nullable = false)
    private String tip;

    @Lob
    @Convert(converter = ReferencesGroupConverter.class)
    private List<ReferencesGroup> referencesGroup;

    public ZoneEntity(
            Long id,
            String name,
            String color,
            List<String> explanations,
            String tip,
            List<ReferencesGroup> referencesGroup
    ) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.explanations = explanations;
        this.tip = tip;
        this.referencesGroup = referencesGroup;
    }

    public static ZoneEntity toEntity(
            Long id,
            String name,
            String color,
            List<String> explanations,
            String tip,
            List<ReferencesGroup> referencesGroup
    ) {
        return new ZoneEntity(id, name, color, explanations, tip, referencesGroup);
    }

    public void assignToResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
    }
}
