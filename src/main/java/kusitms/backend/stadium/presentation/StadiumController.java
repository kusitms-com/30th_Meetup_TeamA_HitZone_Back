package kusitms.backend.stadium.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.StadiumService;
import kusitms.backend.stadium.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.stadium.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.stadium.status.StadiumSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StadiumController {

    private final StadiumService stadiumService;

    /**
     * 구역 추천받은 결과를 DB에 저장한다.
     * @return 저장결과id
     */
    @PostMapping("/zones/recommend")
    public ResponseEntity<ApiResponse<SaveTopRankedZoneResponseDto>> saveRecommendedZones(@Valid @RequestBody SaveTopRankedZoneRequestDto request){
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_SAVE_RECOMMEND_ZONES, stadiumService.saveRecommendedZones(request));
    }

}
