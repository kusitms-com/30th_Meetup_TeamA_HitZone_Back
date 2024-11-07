package kusitms.backend.culture.presentation;

import kusitms.backend.culture.application.EntertainmentService;
import kusitms.backend.culture.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.culture.dto.response.GetFoodsResponseDto;
import kusitms.backend.culture.status.EntertainmentSuccessStatus;
import kusitms.backend.culture.status.FoodSuccessStatus;
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
public class EntertainmentController {

    private final EntertainmentService entertainmentService;

    @GetMapping("/culture/entertainment")
    public ResponseEntity<ApiResponse<GetEntertainmentsResponseDto>> getSuitableEntertainments(
            @RequestParam String stadiumName,
            @RequestParam String boundary
    ) {
        EntertainmentSuccessStatus status;
        if (boundary.equals("내부")) {
            status = EntertainmentSuccessStatus._OK_GET_INTERIOR_ENTERTAINMENTS;
        } else {
            status = EntertainmentSuccessStatus._OK_GET_EXTERIOR_ENTERTAINMENTS;
        }
        return ApiResponse.onSuccess(status, entertainmentService.getSuitableEntertainments(stadiumName, boundary));
    }

}
