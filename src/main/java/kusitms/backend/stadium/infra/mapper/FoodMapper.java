package kusitms.backend.stadium.infra.mapper;


import kusitms.backend.stadium.domain.model.Food;
import kusitms.backend.stadium.infra.jpa.entity.FoodEntity;

public class FoodMapper {

    public static Food toDomain(FoodEntity foodEntity) {
        return Food.toDomain(
                foodEntity.getId(),
                foodEntity.getImgUrl(),
                foodEntity.getBoundary(),
                foodEntity.getCourse(),
                foodEntity.getName(),
                foodEntity.getLocation(),
                foodEntity.getMenu(),
                foodEntity.getPrice(),
                foodEntity.getTip()
        );
    }

    public static FoodEntity toEntity(Food food) {
        return FoodEntity.toEntity(
                food.getId(),
                food.getImgUrl(),
                food.getBoundary(),
                food.getCourse(),
                food.getName(),
                food.getLocation(),
                food.getMenu(),
                food.getPrice(),
                food.getTip()
        );
    }

}

