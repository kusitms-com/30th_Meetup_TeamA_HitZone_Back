package kusitms.backend.culture.application;

import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;
import kusitms.backend.culture.domain.repository.FoodRepository;
import kusitms.backend.culture.dto.response.GetFoodsResponseDto;
import kusitms.backend.culture.status.FoodErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public GetFoodsResponseDto getSuitableFoods(String stadiumName, String boundary, String course) {
        Stadium stadium = stadiumRepository.findByName(stadiumName)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));

        Boundary existBoundary = Boundary.findByName(boundary)
                .orElseThrow(() -> new CustomException(FoodErrorStatus._BAD_REQUEST_BOUNDARY));

        Course existCourse = null;
        if ("내부".equals(boundary)) {
            existCourse = Course.findByName(course)
                    .orElseThrow(() -> new CustomException(FoodErrorStatus._BAD_REQUEST_COURSE));
        }

        List<GetFoodsResponseDto.FoodDto> foods = foodRepository.findFoodsByConditions(stadium, existBoundary, existCourse)
                .stream()
                .map(GetFoodsResponseDto.FoodDto::from)
                .toList();
        return GetFoodsResponseDto.of(foods);
    }
}
