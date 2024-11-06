package kusitms.backend.culture.domain.repository;

import kusitms.backend.culture.domain.entity.Food;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;
import kusitms.backend.stadium.domain.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByStadiumAndBoundaryAndCourse(Stadium stadium, Boundary existBoundary, Course existCourse);
}
