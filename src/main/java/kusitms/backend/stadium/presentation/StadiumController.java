package kusitms.backend.stadium.presentation;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.stadium.application.StadiumService;
import kusitms.backend.stadium.dto.request.TopRankedZoneRequestDto;
import kusitms.backend.stadium.dto.response.TopRankedZoneResponseDto;
import kusitms.backend.stadium.status.StadiumSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StadiumController {

    private final StadiumService stadiumService;

    @PostMapping("/zones/recommend")
    public ResponseEntity<ApiResponse<TopRankedZoneResponseDto>> recommendZones(@RequestBody TopRankedZoneRequestDto request){
        return ApiResponse.onSuccess(StadiumSuccessStatus._OK_RECOMMEND_ZONES, stadiumService.recommendZones(request));
    }
}
