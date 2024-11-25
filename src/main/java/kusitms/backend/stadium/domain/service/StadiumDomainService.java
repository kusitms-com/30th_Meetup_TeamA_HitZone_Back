package kusitms.backend.stadium.domain.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.stadium.domain.enums.StadiumInfo;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static kusitms.backend.stadium.domain.enums.KtWizStadiumStatusType.CHEERING_DUMMY;

@Service
public class StadiumDomainService {

    /**
     * 주어진 경기장 이름에 대한 기본 정보를 조회.
     *
     * @param stadiumName 경기장 이름
     * @return 경기장 기본 정보
     * @throws CustomException 유효하지 않은 경기장 이름이 주어진 경우
     */
    public StadiumInfo getStadiumInfoByName(String stadiumName) {
        return switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> StadiumInfo.LG_HOME;
            case "수원KT위즈파크" -> StadiumInfo.KT_HOME;
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
    }

    /**
     * 주어진 경기장 이름에 대한 상태 타입 배열을 조회.
     *
     * @param stadiumName 경기장 이름
     * @return 경기장 상태 타입 배열
     * @throws CustomException 유효하지 않은 경기장 이름이 주어진 경우
     */
    public StadiumStatusType[] getStatusTypesByName(String stadiumName) {
        return switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
    }

    /**
     * 주어진 상태 타입 배열에서 특정 구역의 이름과 색상을 조회.
     * CHEERING_DUMMY 구역은 제외됩니다.
     *
     * @param statusTypes 상태 타입 배열
     * @return 구역 이름과 색상 정보를 담은 리스트
     */
    public List<GetStadiumInfosResponseDto.ZoneInfo> getZonesNameAndColorFromStadium(StadiumStatusType[] statusTypes) {
        return Arrays.stream(statusTypes)
                .filter(status -> status != CHEERING_DUMMY)
                .map(status -> GetStadiumInfosResponseDto.ZoneInfo.of(status.getZoneName(), status.getZoneColor()))
                .toList();
    }

    /**
     * 주어진 상태 타입 배열에서 특정 구역 이름과 일치하는 구역 정보를 조회.
     *
     * @param statusTypes 상태 타입 배열
     * @param zoneName    구역 이름
     * @return 일치하는 구역 정보
     * @throws CustomException 일치하는 구역을 찾을 수 없는 경우
     */
    public StadiumStatusType findZoneInStadium(StadiumStatusType[] statusTypes, String zoneName) {
        return Arrays.stream(statusTypes)
                .filter(status -> status.getZoneName().equals(zoneName))
                .findFirst()
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_ZONE));
    }
}
