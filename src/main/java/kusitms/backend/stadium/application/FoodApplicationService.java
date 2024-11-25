package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.dto.response.GetFoodsResponseDto;
import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.status.FoodErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodApplicationService {

    private final StadiumApplicationService stadiumApplicationService;

    /**
     * 해당 구장의 boundary와 course에 해당하는 매장들의 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 위치 (내부 or 외부)
     * @param course 식사 코스 (식사 or 후식)
     * @return 매장 정보 리스트 (이미지 Url, boundary, course, 매장명, 위치, 메뉴 리스트, 가격, 팁)
     */
    @Transactional(readOnly = true)
    public GetFoodsResponseDto getFoodsOnCondition(String stadiumName, String boundary, String course) {
        Stadium stadium = stadiumApplicationService.findStadiumByName(stadiumName);
        Boundary existBoundary = Boundary.findByName(boundary)
                .orElseThrow(() -> new CustomException(FoodErrorStatus._BAD_REQUEST_BOUNDARY));
        Course existCourse = null;
        if ("내부".equals(boundary)) {
            existCourse = Course.findByName(course)
                    .orElseThrow(() -> new CustomException(FoodErrorStatus._BAD_REQUEST_COURSE));
        }

        List<GetFoodsResponseDto.FoodDto> foods = stadiumApplicationService.getFoodsOnCondition(stadium, existBoundary, existCourse)
                .stream()
                .map(GetFoodsResponseDto.FoodDto::from)
                .toList();
        return GetFoodsResponseDto.of(foods);
    }
}
