package kusitms.backend.culture.domain.repository;

import kusitms.backend.culture.domain.entity.Food;
import kusitms.backend.culture.domain.repository.custom.FoodRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {
}
