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
@RequestMapping("/api/v1/results")
@Validated
public class ResultController {

    private final ResultApplicationService resultApplicationService;

    /**
     * 클라이언트로부터 받은 키워드에 따른 추천받은 구역 결과를 DB에 저장한다.
     * @param accessToken 쿠키로부터 받은 어세스토큰
     * @param request (스타디움명, 선호구역(1루석, 3루석), 유저 선호 키워드 배열)
     * @return 저장한 Result 엔티티의 id
     */
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<SaveTopRankedZoneResponseDto>> saveRecommendedResult(
            @CookieValue(required = false) String accessToken,
            @Valid @RequestBody SaveTopRankedZoneRequestDto request
    ) {
        SaveTopRankedZoneResponseDto response = resultApplicationService.saveRecommendedResult(accessToken, request);
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_SAVE_RECOMMEND_ZONES, response);
    }

    /**
     * 결과에 해당하는 프로필 정보를 조회한다.
     * @param resultId Result 엔티티의 id
     * @return 결과에 해당하는 프로필 정보(id, 이미지 Url, 닉네임, 타입, 해시태그 리스트)
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<GetProfileResponseDto>> getRecommendedProfile(
            @RequestParam @Min(1L) Long resultId
    ) {
        GetProfileResponseDto response = resultApplicationService.getRecommendedProfile(resultId);
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_GET_RECOMMEND_PROFILE, response);
    }

    /**
     * 결과에 해당하는 추천구역 리스트를 조회한다.
     * @param resultId Result 엔티티의 id
     * @param count 클라이언트측에서 받고자 하는 추천구역의 개수
     * @return 추천구역의 리스트(id, 구역명, 구역색, 설명 리스트, 팁, 참고사항 리스트)
     */
    @GetMapping("/zones")
    public ResponseEntity<ApiResponse<GetZonesResponseDto>> getRecommendedZones(
            @RequestParam @Min(1L) Long resultId,
            @RequestParam @Min(1L) Long count
    ) {
        GetZonesResponseDto response = resultApplicationService.getRecommendedZones(resultId, count);
        return ApiResponse.onSuccess(ResultSuccessStatus._OK_GET_RECOMMEND_ZONES, response);
    }
}
