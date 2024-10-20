package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.result.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.result.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.result.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.dto.response.GetZoneGuideResponseDto;
import kusitms.backend.stadium.dto.response.GetZonesNameResponseDto;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public GetZonesNameResponseDto getZoneName(String stadiumName) {
        List<String> zoneNames = switch (stadiumName) {
            case "잠실종합운동장" -> getZoneNamesFromStadium(JamsilStadiumStatusType.values());
            case "수원KT위즈파크" -> getZoneNamesFromStadium(KtWizStadiumStatusType.values());
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };

        return GetZonesNameResponseDto.of(zoneNames);
    }

    @Transactional(readOnly = true)
    public GetZoneGuideResponseDto getZoneGuide(String stadiumName, String zoneName) {
        Stadium stadium = stadiumRepository.findByName(stadiumName)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));

        StadiumStatusType zoneType = switch (stadiumName) {
            case "잠실종합운동장" -> findZoneInStadium(JamsilStadiumStatusType.values(), zoneName);
            case "수원KT위즈파크" -> findZoneInStadium(KtWizStadiumStatusType.values(), zoneName);
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };

        return GetZoneGuideResponseDto.from(zoneType);
    }

    private List<String> getZoneNamesFromStadium(StadiumStatusType[] statusTypes) {
        return Arrays.stream(statusTypes)
                .map(StadiumStatusType::getZoneName)
                .toList();
    }

    private StadiumStatusType findZoneInStadium(StadiumStatusType[] statusTypes, String zoneName) {
        return Arrays.stream(statusTypes)
                .filter(status -> status.getZoneName().equals(zoneName))
                .findFirst()
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_ZONE));
    }

}
