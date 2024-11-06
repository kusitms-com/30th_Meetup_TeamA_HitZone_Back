package kusitms.backend.culture.dto.response;

import kusitms.backend.culture.domain.entity.Food;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;

import java.util.List;

public record GetFoodsResponseDto(List<FoodDto> foods) {

    public record FoodDto(Boundary boundary, Course course, String name, String location, List<String> menu, String price, String explanation) {
        public static FoodDto from(Food food) {
            return new FoodDto(food.getBoundary(), food.getCourse(), food.getName(), food.getLocation(), food.getMenu(), food.getPrice(), food.getExplanation());
        }
    }

    public static GetFoodsResponseDto of(List<FoodDto> foods) {
        return new GetFoodsResponseDto(foods);
    }
}
