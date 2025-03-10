package kusitms.backend.stadium.presentation;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.application.dto.response.GetZoneGuideResponseDto;
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
@RequestMapping("/api/v1/stadium")
@Validated
public class StadiumController {

    private final StadiumApplicationService stadiumApplicationService;

    /**
     * 해당 스타디움의 홈 이미지, 구역 이름 & 색상 리스트를 조회한다.
     * @param stadiumName 스타디움명
     * @return 홈 이미지, 구역 이름 & 색상 리스트
     */
    @GetMapping("/zones")
    public ResponseEntity<ApiResponse<GetStadiumInfosResponseDto>> getStadiumInfos(
            @RequestParam @NotBlank String stadiumName
    ) {
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_GET_ZONE_INFOS, stadiumApplicationService.getStadiumInfos(stadiumName));
    }

    /**
     * 해당 스타디움 구역의 정보와 팁을 반환한다.
     * @param stadiumName 스타디움명
     * @param zoneName 구역명
     * @return 구역 정보(구역명, 구역색깔, 한줄 설명, 출입구, 단차 간격, 좌석 간격, 팁, 참고사항)
     */
    @GetMapping("/zones/guide")
    public ResponseEntity<ApiResponse<GetZoneGuideResponseDto>> getZoneGuide(
            @RequestParam @NotBlank String stadiumName,
            @RequestParam @NotBlank String zoneName
    ) {
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_GET_ZONE_GUIDE, stadiumApplicationService.getZoneGuide(stadiumName, zoneName));
    }
}
