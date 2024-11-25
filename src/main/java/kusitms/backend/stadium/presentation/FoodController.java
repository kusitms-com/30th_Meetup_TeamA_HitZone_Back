package kusitms.backend.stadium.presentation;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.FoodApplicationService;
import kusitms.backend.stadium.application.dto.response.GetFoodsResponseDto;
import kusitms.backend.stadium.status.FoodSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/foods")
@Validated
public class FoodController {

    private final FoodApplicationService foodApplicationService;

    /**
     * 해당 구장의 boundary와 course에 해당하는 매장들의 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 매장 위치 (내부 or 외부)
     * @param course 음식 종류 (식사 or 후식)
     * @return 매장 정보 리스트 (이미지 Url, boundary, course, 매장명, 위치, 메뉴 리스트, 가격, 팁)
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<GetFoodsResponseDto>> getSuitableFoods(
            @RequestParam @NotBlank String stadiumName,
            @RequestParam @NotBlank String boundary,
            @RequestParam(required = false) String course
    ) {
        FoodSuccessStatus status;
        if ("내부".equals(boundary) && "식사".equals(course)) {
            status = FoodSuccessStatus._OK_GET_INTERIOR_MEALS;
        } else if ("내부".equals(boundary) && "후식".equals(course)) {
            status = FoodSuccessStatus._OK_GET_INTERIOR_DESSERTS;
        } else {
            status = FoodSuccessStatus._OK_GET_EXTERIOR_TOTAL;
        }
        return ApiResponse.onSuccess(status, foodApplicationService.getFoodsOnCondition(stadiumName, boundary, course));
    }
}
