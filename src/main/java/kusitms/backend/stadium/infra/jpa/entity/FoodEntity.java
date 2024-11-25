package kusitms.backend.stadium.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.infra.converter.StringListConverter;
import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food")
public class FoodEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private StadiumEntity stadiumEntity;

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

    public FoodEntity(
            Long id,
            String imgUrl,
            Boundary boundary,
            Course course,
            String name,
            String location,
            List<String> menu,
            String price,
            String tip
    ) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.boundary = boundary;
        this.course = course;
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.price = price;
        this.tip = tip;
    }

    public static FoodEntity toEntity(
            Long id,
            String imgUrl,
            Boundary boundary,
            Course course,
            String name,
            String location,
            List<String> menu,
            String price,
            String tip
    ) {
        return new FoodEntity(id, imgUrl, boundary, course, name, location, menu, price, tip);
    }

    public void assignToStadiumEntity(StadiumEntity stadiumEntity) {
        this.stadiumEntity = stadiumEntity;
    }
}
