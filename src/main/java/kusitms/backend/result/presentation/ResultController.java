package kusitms.backend.result.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.result.application.ResultApplicationService;
import kusitms.backend.result.application.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.application.dto.response.GetProfileResponseDto;
import kusitms.backend.result.application.dto.response.GetZonesResponseDto;
import kusitms.backend.result.application.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.status.ResultSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Validated
public class ResultController {

    private final ResultApplicationService resultApplicationService;

    /**
     * 구역 추천받은 결과를 DB에 저장한다.
     * @return 저장결과id
     */
    @PostMapping("/results/save")
    public ResponseEntity<ApiResponse<SaveTopRankedZoneResponseDto>> saveRecommendedZones(
            @CookieValue(required = false) String accessToken,
            @Valid @RequestBody SaveTopRankedZoneRequestDto request
    ) {
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_SAVE_RECOMMEND_ZONES, resultApplicationService.saveRecommendedZones(accessToken, request));
    }

    /**
     * 해당 결과의 프로필 정보를 조회한다.
     * @return 프로필id, 닉네임, 타입, 설명, 해시태그
     */
    @GetMapping("/results/profile")
    public ResponseEntity<ApiResponse<GetProfileResponseDto>> getRecommendedProfile(
            @RequestParam @Min(1L) Long resultId
    ) {
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_GET_RECOMMEND_PROFILE, resultApplicationService.getRecommendedProfile(resultId));
    }

    /**
     * 해당 결과의 추천구역 리스트를 조회한다.
     * @return 추천구역 리스트
     */
    @GetMapping("/results/zones")
    public ResponseEntity<ApiResponse<GetZonesResponseDto>> getRecommendedZones(
            @RequestParam @Min(1L) Long resultId,
            @RequestParam @Min(1L) Long count
    ) {
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_GET_RECOMMEND_ZONES, resultApplicationService.getRecommendedZones(resultId, count));
    }

}
