package kusitms.backend.culture.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.infra.converter.StringListConverter;
import kusitms.backend.stadium.domain.entity.Stadium;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Column(nullable = false)
    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Boundary boundary;

    @Enumerated(EnumType.STRING)
    private Course course;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> menu;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String tip;

}
