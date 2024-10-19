package kusitms.backend.result.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.result.application.ResultService;
import kusitms.backend.result.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.dto.response.GetProfileResponseDto;
import kusitms.backend.result.dto.response.GetZonesResponseDto;
import kusitms.backend.result.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.status.ResultSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ResultController {

    private final ResultService resultService;

    /**
     * 구역 추천받은 결과를 DB에 저장한다.
     * @return 저장결과id
     */
    @PostMapping("/zones/recommend")
    public ResponseEntity<ApiResponse<SaveTopRankedZoneResponseDto>> saveRecommendedZones(
            @CookieValue(required = false) String accessToken,
            @Valid @RequestBody SaveTopRankedZoneRequestDto request
    ) {
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_SAVE_RECOMMEND_ZONES, resultService.saveRecommendedZones(accessToken, request));
    }

    /**
     * 해당 결과의 프로필 정보를 조회한다.
     * @return 프로필id, 닉네임, 타입, 설명, 해시태그
     */
    @GetMapping("/profiles")
    public ResponseEntity<ApiResponse<GetProfileResponseDto>> getRecommendedProfile(
            @RequestParam Long resultId
    ) {
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_GET_RECOMMEND_PROFILE, resultService.getRecommendedProfile(resultId));
    }

    @GetMapping("/zones/recommend")
    public ResponseEntity<ApiResponse<GetZonesResponseDto>> getRecommendedZones(
            @RequestParam Long resultId,
            @RequestParam Long count
    ) {
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_GET_RECOMMEND_ZONES, resultService.getRecommendedZones(resultId, count));
    }

}
