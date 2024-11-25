package kusitms.backend.stadium.infra.jpa.repository.custom;

import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.infra.jpa.entity.FoodEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepositoryCustom {
    List<FoodEntity> findFoodsByConditions(Stadium stadium, Boundary boundary, Course course);
}
