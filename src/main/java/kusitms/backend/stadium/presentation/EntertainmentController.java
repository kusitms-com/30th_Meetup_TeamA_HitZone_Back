package kusitms.backend.stadium.presentation;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.EntertainmentApplicationService;
import kusitms.backend.stadium.application.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.stadium.status.StadiumSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entertainments")
@Validated
public class EntertainmentController {

    private final EntertainmentApplicationService entertainmentApplicationService;

    /**
     * 해당 구장의 boundary에 해당하는 즐길거리의 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 매장 위치 (내부 or 외부)
     * @return 즐길거리 리스트 (이미지 Url, boundary, 이름, 설명 리스트, 팁 리스트)
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<GetEntertainmentsResponseDto>> getSuitableEntertainments(
            @RequestParam @NotBlank String stadiumName,
            @RequestParam @NotBlank String boundary
    ) {
        StadiumSuccessStatus status;
        if ("내부".equals(boundary)) {
            status = StadiumSuccessStatus._OK_GET_INTERIOR_ENTERTAINMENTS;
        } else {
            status = StadiumSuccessStatus._OK_GET_EXTERIOR_ENTERTAINMENTS;
        }
        return ApiResponse.onSuccess(status, entertainmentApplicationService.getSuitableEntertainments(stadiumName, boundary));
    }
}
