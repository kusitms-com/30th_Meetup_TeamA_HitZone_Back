package kusitms.backend.stadium.domain.repository;

import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Food;
import kusitms.backend.stadium.domain.model.Stadium;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepository {
    Stadium findStadiumByName(String stadiumName);
    List<Food> getFoodsOnCondition(Stadium stadium, Boundary boundary, Course course);
}
