package kusitms.backend.stadium.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.stadium.common.ReferencesGroup;
import kusitms.backend.stadium.domain.converter.ReferencesGroupConverter;
import kusitms.backend.stadium.domain.converter.StringListConverter;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
    @Convert(converter = ReferencesGroupConverter.class)  // referencesGroup을 JSON으로 직렬화
    private List<ReferencesGroup> referencesGroup;

}
