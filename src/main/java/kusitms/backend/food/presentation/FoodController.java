package kusitms.backend.food.presentation;

import kusitms.backend.food.application.FoodService;
import kusitms.backend.food.dto.response.GetFoodsResponseDto;
import kusitms.backend.food.status.FoodSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FoodController {

    private final FoodService foodService;

    /**
     * 해당 boundary와 course에 해당하는 매장들의 리스트를 반환한다.
     * @param boundary
     * @param course
     * @return
     */
    @GetMapping("/foods")
    public ResponseEntity<ApiResponse<GetFoodsResponseDto>> getSuitableFoods(
            @RequestParam String stadiumName,
            @RequestParam String boundary,
            @RequestParam String course
    ) {
        FoodSuccessStatus status;
        if (boundary.equals("내부") && course.equals("식사")) {
            status = FoodSuccessStatus._OK_GET_INTERIOR_MEALS;
        } else if (boundary.equals("내부") && course.equals("후식")) {
            status = FoodSuccessStatus._OK_GET_INTERIOR_DESSERTS;
        } else {
            status = FoodSuccessStatus._OK_GET_EXTERIOR_TOTAL;
        }
        return ApiResponse.onSuccess(status, foodService.getSuitableFoods(stadiumName, boundary, course));
    }


}
