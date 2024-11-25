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

@Service
public class StadiumDomainService {

    /**
     * 스타디움명을 통하여 스타디움 정보를 조회한다.
     * @param stadiumName 스타디움명
     * @return 스타디움 정보 (이미지 Url, 1루팀, 3루팀)
     */
    public StadiumInfo getStadiumInfoByName(String stadiumName) {
        return switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> StadiumInfo.LG_HOME;
            case "수원KT위즈파크" -> StadiumInfo.KT_HOME;
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
    }

    /**
     * 스타디움명을 토대로 구역 가이드 정보 리스트를 조회한다.
     * @param stadiumName 스타디움명
     * @return 구역 가이드 정보 리스트 (이미지 Url, 구역명, 구역 색상, 한줄 설명, 설명 리스트, 1루팀, 3루팀, 팁, 참고 사항 리스트, 키워드 리스트, 입구, 단차, 좌석, 유용한 점)
     */
    public StadiumStatusType[] getStatusTypesByName(String stadiumName) {
        return switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
    }

    /**
     * 구역 가이드 정보에서 이미지 Url, 1루팀, 3루팀, 구역명, 구역 색상만 추출한다.
     * @param statusTypes 구역 가이드 정보 리스트
     * @return 구역 정보 리스트 (이미지 Url, 1루팀, 3루팀, 구역명, 구역 색상)
     */
    public List<GetStadiumInfosResponseDto.ZoneInfo> getZonesNameAndColorFromStadium(StadiumStatusType[] statusTypes) {
        return Arrays.stream(statusTypes)
                .map(status -> GetStadiumInfosResponseDto.ZoneInfo.of(status.getZoneName(), status.getZoneColor()))
                .toList();
    }

    /**
     * 구역 리스트에서 구역명에 해당하는 것만 추출한다.
     * @param statusTypes 구역가이드 정보 리스트
     * @param zoneName 구역명
     * @return
     */
    public StadiumStatusType findZoneInStadium(StadiumStatusType[] statusTypes, String zoneName) {
        return Arrays.stream(statusTypes)
                .filter(status -> status.getZoneName().equals(zoneName))
                .findFirst()
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_ZONE));
    }
}
