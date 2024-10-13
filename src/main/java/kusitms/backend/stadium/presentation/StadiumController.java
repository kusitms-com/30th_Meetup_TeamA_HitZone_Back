package kusitms.backend.stadium.presentation;

import kusitms.backend.stadium.domain.entity.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.dto.request.TopRankedZoneRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StadiumController {

    @PostMapping("/zone")
    public Object serveZones(@RequestBody TopRankedZoneRequestDto request){
        return JamsilStadiumStatusType.getTopRankedZones(List.of(request.clientKeywords()));
    }
}
