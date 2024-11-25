package kusitms.backend.stadium.presentation;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.application.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.stadium.application.dto.response.GetFoodsResponseDto;
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
@RequestMapping("/api/v1")
@Validated
public class StadiumController {

    private final StadiumApplicationService stadiumApplicationService;

    /**
     * 해당 스타디움의 홈 이미지, 구역 이름 & 색상 리스트를 조회한다.
     * @param stadiumName 스타디움명
     * @return 홈 이미지, 구역 이름 & 색상 리스트
     */
    @GetMapping("/stadium/zones")
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
    @GetMapping("/stadium/zones/guide")
    public ResponseEntity<ApiResponse<GetZoneGuideResponseDto>> getZoneGuide(
            @RequestParam @NotBlank String stadiumName,
            @RequestParam @NotBlank String zoneName
    ) {
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_GET_ZONE_GUIDE, stadiumApplicationService.getZoneGuide(stadiumName, zoneName));
    }

    /**
     * 해당 구장의 boundary에 해당하는 즐길거리의 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 매장 위치 (내부 or 외부)
     * @return 즐길거리 리스트 (이미지 Url, boundary, 이름, 설명 리스트, 팁 리스트)
     */
    @GetMapping("/culture/entertainments")
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
        return ApiResponse.onSuccess(status, stadiumApplicationService.getSuitableEntertainments(stadiumName, boundary));
    }

    /**
     * 해당 구장의 boundary와 course에 해당하는 매장들의 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 매장 위치 (내부 or 외부)
     * @param course 음식 종류 (식사 or 후식)
     * @return 매장 정보 리스트 (이미지 Url, boundary, course, 매장명, 위치, 메뉴 리스트, 가격, 팁)
     */
    @GetMapping("/culture/foods")
    public ResponseEntity<ApiResponse<GetFoodsResponseDto>> getSuitableFoods(
            @RequestParam @NotBlank String stadiumName,
            @RequestParam @NotBlank String boundary,
            @RequestParam(required = false) String course
    ) {
        StadiumSuccessStatus status;
        if ("내부".equals(boundary) && "식사".equals(course)) {
            status = StadiumSuccessStatus._OK_GET_INTERIOR_MEALS;
        } else if ("내부".equals(boundary) && "후식".equals(course)) {
            status = StadiumSuccessStatus._OK_GET_INTERIOR_DESSERTS;
        } else {
            status = StadiumSuccessStatus._OK_GET_EXTERIOR_TOTAL;
        }
        return ApiResponse.onSuccess(status, stadiumApplicationService.getFoodsOnCondition(stadiumName, boundary, course));
    }

}
