package kusitms.backend.food.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.food.domain.enums.Boundary;
import kusitms.backend.food.domain.enums.Course;
import kusitms.backend.stadium.domain.entity.Stadium;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Enumerated(EnumType.STRING)
    private Boundary boundary;

    @Enumerated(EnumType.STRING)
    private Course course;



}
