package kusitms.backend.stadium.presentation;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.StadiumService;
import kusitms.backend.stadium.dto.response.GetZoneGuideResponseDto;
import kusitms.backend.stadium.dto.response.GetZonesNameResponseDto;
import kusitms.backend.stadium.status.StadiumSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StadiumController {

    private final StadiumService stadiumService;

    /**
     * 해당 스타디움의 구역 이름들을 조회한다.
     * @return 구역 이름들 리스트
     */
    @GetMapping("/zones/name")
    public ResponseEntity<ApiResponse<GetZonesNameResponseDto>> getZonesName(
            @RequestParam String stadiumName
    ) {
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_GET_ZONES_NAME, stadiumService.getZoneName(stadiumName));
    }


    /**
     * 해당 스타디움 구역의 정보와 팁을 반환한다.
     * @return 구역 정보(한줄 설명, 출입구, 단차 간격, 좌석 간격, 팁, 참고사항)
     */
    @GetMapping("/zones/guide")
    public ResponseEntity<ApiResponse<GetZoneGuideResponseDto>> getZoneGuide(
            @RequestParam String stadiumName,
            @RequestParam String zoneName
    ) {
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_GET_ZONE_GUIDE, stadiumService.getZoneGuide(stadiumName, zoneName));
    }

}
