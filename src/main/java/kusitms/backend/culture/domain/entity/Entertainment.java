package kusitms.backend.culture.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.domain.converter.StringListConverter;
import kusitms.backend.stadium.domain.entity.Stadium;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Entertainment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entertainment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Column(nullable = false)
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Boundary boundary;

    @Column(nullable = false)
    private String name;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> explanation;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> tip;
}
