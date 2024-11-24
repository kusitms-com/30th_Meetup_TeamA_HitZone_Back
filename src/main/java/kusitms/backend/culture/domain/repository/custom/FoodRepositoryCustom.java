package kusitms.backend.culture.domain.repository.custom;

import kusitms.backend.culture.domain.entity.Food;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Stadium;

import java.util.List;

public interface FoodRepositoryCustom {
    List<Food> findFoodsByConditions(Stadium stadium, Boundary boundary, Course course);
}
