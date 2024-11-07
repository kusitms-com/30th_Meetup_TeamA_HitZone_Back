package kusitms.backend.culture.presentation;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.culture.application.EntertainmentService;
import kusitms.backend.culture.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.culture.status.EntertainmentSuccessStatus;
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


    /**
     * 해당 구장의 즐길 거리 목록을 조회한다.
     * @param stadiumName
     * @param boundary
     * @return
     */
    @GetMapping("/culture/entertainments")
    public ResponseEntity<ApiResponse<GetEntertainmentsResponseDto>> getSuitableEntertainments(
            @RequestParam String stadiumName,
            @RequestParam String boundary
    ) {
        EntertainmentSuccessStatus status;
        if ("내부".equals(boundary)) {
            status = EntertainmentSuccessStatus._OK_GET_INTERIOR_ENTERTAINMENTS;
        } else {
            status = EntertainmentSuccessStatus._OK_GET_EXTERIOR_ENTERTAINMENTS;
        }
        return ApiResponse.onSuccess(status, entertainmentService.getSuitableEntertainments(stadiumName, boundary));
    }

}
