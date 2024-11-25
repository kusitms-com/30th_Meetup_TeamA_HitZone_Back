package kusitms.backend.stadium.infra.jpa.repositoryImpl;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Food;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.infra.jpa.repository.StadiumJpaRepository;
import kusitms.backend.stadium.infra.jpa.repository.custom.StadiumCustomRepository;
import kusitms.backend.stadium.infra.mapper.FoodMapper;
import kusitms.backend.stadium.infra.mapper.StadiumMapper;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StadiumRepositoryJpaImpl implements StadiumRepository {

    private final StadiumJpaRepository stadiumJpaRepository;
    private final StadiumCustomRepository stadiumCustomRepository;

    @Override
    public Stadium findStadiumByName(String stadiumName) {
        return stadiumJpaRepository.findByName(stadiumName)
                .map(StadiumMapper::toDomain)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));
    }

    @Override
    public List<Food> getFoodsOnCondition(Stadium stadium, Boundary boundary, Course course) {
        return stadiumCustomRepository.findFoodsByConditions(stadium,boundary,course)
                .stream()
                .map(FoodMapper::toDomain)
                .toList();
    }
}
