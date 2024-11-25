package kusitms.backend.stadium.domain.model;

import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium extends BaseTimeEntity {

    private Long id;
    private String name;
    private List<Food> foods = new ArrayList<>();
    private List<Entertainment> entertainments = new ArrayList<>();

    public Stadium(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static Stadium toDomain(
            Long id,
            String name
    ) {
        return new Stadium(id, name);
    }

    public void addFood(Food food) {
        this.foods.add(food);
        food.assignToStadium(this);
    }

    public void addEntertainment(Entertainment entertainment){
        this.entertainments.add(entertainment);
        entertainment.assignToStadium(this);
    }

}
