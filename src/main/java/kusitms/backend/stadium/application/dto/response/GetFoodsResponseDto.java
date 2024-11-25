package kusitms.backend.stadium.application.dto.response;

import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Food;

import java.util.List;

public record GetFoodsResponseDto(
        List<FoodDto> foods
) {
    public record FoodDto(
            String imgUrl,
            Boundary boundary,
            Course course,
            String name,
            String location,
            List<String> menu,
            String price,
            String tip
    ) {
        public static FoodDto from(Food food) {
            return new FoodDto(
                    food.getImgUrl(),
                    food.getBoundary(),
                    food.getCourse(),
                    food.getName(),
                    food.getLocation(),
                    food.getMenu(),
                    food.getPrice(),
                    food.getTip());
        }
    }

    public static GetFoodsResponseDto of(List<FoodDto> foods) {
        return new GetFoodsResponseDto(foods);
    }
}
