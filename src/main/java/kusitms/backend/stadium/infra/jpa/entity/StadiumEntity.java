package kusitms.backend.stadium.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "stadiumEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodEntity> foodEntities = new ArrayList<>();

    @OneToMany(mappedBy = "stadiumEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntertainmentEntity> entertainmentEntities = new ArrayList<>();

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

    public void addFoodEntity(FoodEntity foodEntity) {
        this.foodEntities.add(foodEntity);
        foodEntity.assignToStadiumEntity(this);
    }

    public void addEntertainmentEntity(EntertainmentEntity entertainmentEntity) {
        this.entertainmentEntities.add(entertainmentEntity);
        entertainmentEntity.assignToStadiumEntity(this);
    }
}
