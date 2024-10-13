package kusitms.backend.stadium.presentation;

import kusitms.backend.stadium.common.TopRankedZones;
import kusitms.backend.stadium.domain.entity.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.dto.request.TopRankedZoneRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StadiumController {

    @PostMapping("/zone")
    public Object serveZones(@RequestBody TopRankedZoneRequestDto request){
        List<Map<String, Object>> jamsilTopZones = TopRankedZones.getTopRankedZones(
                JamsilStadiumStatusType.values(), "잠실", List.of(request.clientKeywords()));

        return jamsilTopZones;
    }
}
