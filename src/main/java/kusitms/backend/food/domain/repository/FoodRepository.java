package kusitms.backend.food.domain.repository;

import kusitms.backend.food.domain.entity.Food;
import kusitms.backend.food.domain.enums.Boundary;
import kusitms.backend.food.domain.enums.Course;
import kusitms.backend.food.dto.response.GetFoodsResponseDto;
import kusitms.backend.stadium.domain.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByStadiumAndBoundaryAndCourse(Stadium stadium, Boundary existBoundary, Course existCourse);
}
