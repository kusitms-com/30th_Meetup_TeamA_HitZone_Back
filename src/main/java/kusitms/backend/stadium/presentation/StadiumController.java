package kusitms.backend.stadium.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StadiumController {

    private final StadiumService stadiumService;

    /**
     * 해당 스타디움 구역의 정보와 팁을 반환한다.
     * @return 구역 정보(한줄 설명, 출입구, 단차 간격, 좌석 간격, 팁, 참고사항)
     */
    public ResponseEntity<ApiResponse<Object>> getZoneGuide(
            @RequestParam String stadiumName,
            @RequestParam String zoneName
    ) {
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_GET_ZONE_GUIDE, stadiumService.getZoneGuide(stadiumName, zoneName));
    }

}
