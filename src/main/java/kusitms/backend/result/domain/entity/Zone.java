package kusitms.backend.result.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.common.ReferencesGroup;
import kusitms.backend.result.domain.converter.ReferencesGroupConverter;
import kusitms.backend.result.domain.converter.StringListConverter;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "recommend_zone")
public class Zone extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zone_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @Column(nullable = false)
    private String name;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> explanations;

    @Column(nullable = false)
    private String tip;

    @Lob
    @Convert(converter = ReferencesGroupConverter.class)
    private List<ReferencesGroup> referencesGroup;

}
