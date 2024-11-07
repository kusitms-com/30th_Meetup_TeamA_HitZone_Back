package kusitms.backend.culture.presentation;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.culture.application.FoodService;
import kusitms.backend.culture.dto.response.GetFoodsResponseDto;
import kusitms.backend.culture.status.FoodSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class FoodController {

    private final FoodService foodService;

    /**
     * 해당 boundary와 course에 해당하는 매장들의 리스트를 반환한다.
     * @param boundary
     * @param course
     * @return
     */
    @GetMapping("/culture/foods")
    public ResponseEntity<ApiResponse<GetFoodsResponseDto>> getSuitableFoods(
            @RequestParam @NotBlank String stadiumName,
            @RequestParam @NotBlank String boundary,
            @RequestParam(required = false) @NotBlank String course
    ) {
        FoodSuccessStatus status;
        if ("내부".equals(boundary) && "식사".equals(course)) {
            status = FoodSuccessStatus._OK_GET_INTERIOR_MEALS;
        } else if ("내부".equals(boundary) && "후식".equals(course)) {
            status = FoodSuccessStatus._OK_GET_INTERIOR_DESSERTS;
        } else {
            status = FoodSuccessStatus._OK_GET_EXTERIOR_TOTAL;
        }
        return ApiResponse.onSuccess(status, foodService.getSuitableFoods(stadiumName, boundary, course));
    }


}
