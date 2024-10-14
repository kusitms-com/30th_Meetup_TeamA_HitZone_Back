package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.common.TopRankedZones;
import kusitms.backend.stadium.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.dto.request.TopRankedZoneRequestDto;
import kusitms.backend.stadium.dto.response.TopRankedZoneResponseDto;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StadiumService {

    @Transactional
    public <T extends Enum<T> & StadiumStatusType> TopRankedZoneResponseDto recommendZones(TopRankedZoneRequestDto request) {

        T[] zones = switch (request.stadium()) {
            case "잠실종합운동장" -> (T[]) JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> (T[]) KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._BAD_REQUEST_STADIUM);
        };

        List<Map<String, Object>> recommendZones = TopRankedZones.getTopRankedZones(
                zones, List.of(request.clientKeywords()));
        return TopRankedZoneResponseDto.builder()
                .stadium(request.stadium())
                .preference(request.preference())
                .recommendZones(recommendZones)
                .build();
    }
}
