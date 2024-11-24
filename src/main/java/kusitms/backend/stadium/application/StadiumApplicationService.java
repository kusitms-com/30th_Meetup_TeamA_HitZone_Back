package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.stadium.domain.enums.StadiumInfo;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.application.dto.response.GetZoneGuideResponseDto;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StadiumApplicationService {

    private final StadiumRepository stadiumRepository;

    public <T extends Enum<T> & StadiumStatusType> T[] extractZonesByStadiumName (String stadiumName){
        T[] zones = switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> (T[]) JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> (T[]) KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
        log.info("해당 스타디움의 구장리스트 반환 완료");
        return zones;
    }

    @Transactional(readOnly = true)
    public Long getIdByStadiumName(String staiumName) {
        Stadium stadium = stadiumRepository.findByName(staiumName)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));
        log.info("The Corresponding stadium Id is " + stadium.getId());
        return stadium.getId();
    }

    @Transactional(readOnly = true)
    public GetStadiumInfosResponseDto getStadiumInfos(String stadiumName) {
        StadiumInfo stadiumInfo = getStadiumInfoByName(stadiumName);
        List<GetStadiumInfosResponseDto.ZoneInfo> zoneInfos = getZonesNameAndColorFromStadium(getStatusTypesByName(stadiumName));

        return GetStadiumInfosResponseDto.of(stadiumInfo.getImgUrl(), stadiumInfo.getFirstBaseSide(), stadiumInfo.getThirdBaseSide(), zoneInfos);
    }

    private StadiumInfo getStadiumInfoByName(String stadiumName) {
        return switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> StadiumInfo.LG_HOME;
            case "수원KT위즈파크" -> StadiumInfo.KT_HOME;
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
    }

    private StadiumStatusType[] getStatusTypesByName(String stadiumName) {
        return switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
    }

    @Transactional(readOnly = true)
    public GetZoneGuideResponseDto getZoneGuide(String stadiumName, String zoneName) {
        StadiumStatusType zoneType = switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> findZoneInStadium(JamsilStadiumStatusType.values(), zoneName);
            case "수원KT위즈파크" -> findZoneInStadium(KtWizStadiumStatusType.values(), zoneName);
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
        return GetZoneGuideResponseDto.from(zoneType);
    }

    private List<GetStadiumInfosResponseDto.ZoneInfo> getZonesNameAndColorFromStadium(StadiumStatusType[] statusTypes) {
        return Arrays.stream(statusTypes)
                .map(status -> GetStadiumInfosResponseDto.ZoneInfo.of(status.getZoneName(), status.getZoneColor()))
                .toList();
    }

    private StadiumStatusType findZoneInStadium(StadiumStatusType[] statusTypes, String zoneName) {
        return Arrays.stream(statusTypes)
                .filter(status -> status.getZoneName().equals(zoneName))
                .findFirst()
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_ZONE));
    }
}