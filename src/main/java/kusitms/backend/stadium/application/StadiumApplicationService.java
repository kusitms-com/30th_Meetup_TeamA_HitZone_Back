package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.application.dto.response.GetZoneGuideResponseDto;
import kusitms.backend.stadium.domain.enums.*;
import kusitms.backend.stadium.domain.model.Food;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.domain.service.StadiumDomainService;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StadiumApplicationService {

    private final StadiumDomainService stadiumDomainService;
    private final StadiumRepository stadiumRepository;

    /**
     * 해당 스타디움의 모든 구역 정보 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @return 해당 스타디움의 구역 리스트 (정보 포함)
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    public <T extends Enum<T> & StadiumStatusType> T[] extractZonesByStadiumName (String stadiumName){
        T[] zones = switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> (T[]) JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> (T[]) KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
        log.info("해당 스타디움의 구장 리스트 반환 완료");
        return zones;
    }

    /**
     * 스타디움명을 통해 해당 스타디움의 id를 반환한다.
     * @param staiumName 스타디움명
     * @return 해당 스타디움의 id
     */
    @Transactional(readOnly = true)
    public Long getIdByStadiumName(String staiumName) {
        Stadium stadium = findStadiumByName(staiumName);
        log.info("The Corresponding stadium Id is {}", stadium.getId());
        return stadium.getId();
    }

    /**
     * 해당 스타디움명을 통하여 스타디움 정보를 반환한다.
     * @param stadiumName 스타디움명
     * @return 스타디움 정보 (이미지 Url, 1루석 팀, 3루석 팀, 구역명, 구역 색상)
     */
    public GetStadiumInfosResponseDto getStadiumInfos(String stadiumName) {
        StadiumInfo stadiumInfo = stadiumDomainService.getStadiumInfoByName(stadiumName);
        StadiumStatusType[] stadiumStatusTypes = stadiumDomainService.getStatusTypesByName(stadiumName);
        List<GetStadiumInfosResponseDto.ZoneInfo> zoneInfos = stadiumDomainService.getZonesNameAndColorFromStadium(stadiumStatusTypes);
        log.info("The Corresponding stadium info is returned");
        return GetStadiumInfosResponseDto.of(stadiumInfo.getImgUrl(), stadiumInfo.getFirstBaseSide(), stadiumInfo.getThirdBaseSide(), zoneInfos);
    }

    /**
     * 해당 스타디움, 구역의 정보를 반환한다.
     * @param stadiumName 스다티움명
     * @param zoneName 구역명
     * @return 해당 구역 정보 (이미지 Url, 구역명, 구역 색상, 설명, 1루팀, 3루팀, 입구, 단차, 좌석 거리, 유용한 점, 팁, 참고 사항)
     */
    public GetZoneGuideResponseDto getZoneGuide(String stadiumName, String zoneName) {
        StadiumStatusType zoneType = switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> stadiumDomainService.findZoneInStadium(JamsilStadiumStatusType.values(), zoneName);
            case "수원KT위즈파크" -> stadiumDomainService.findZoneInStadium(KtWizStadiumStatusType.values(), zoneName);
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
        log.info("구역명에 해당하는 구역 정보를 조회하였습니다.");
        return GetZoneGuideResponseDto.from(zoneType);
    }

    /**
     * 해당 스타디움의 boundary의 즐길거리 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 위치 (내부 or 외부)
     * @return 즐길거리 리스트 (이미지Url, boundary, 이름, 설명 리스트, 팁 리스트)
     */
    @Transactional(readOnly = true)
    public GetEntertainmentsResponseDto getSuitableEntertainments(String stadiumName, String boundary) {
        Stadium stadium = findStadiumByName(stadiumName);
        log.info("The Corresponding stadium Id is " + stadium.getEntertainments());
        Boundary existBoundary = Boundary.findByName(boundary)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._BAD_REQUEST_BOUNDARY));

        List<GetEntertainmentsResponseDto.EntertainmentDto> entertainments = stadiumDomainService.getEntertainmentsOnCondition(stadium, existBoundary);
        log.info("조건에 해당하는 즐길거리 목록이 조회되었습니다.");
        return GetEntertainmentsResponseDto.of(entertainments);
    }

    /**
     * 스타디움명을 토대로 해당 스타디움 객체를 반환한다.
     * @param stadiumName 스타디움명
     * @return 스타디움 도메인 객체
     */
    @Transactional(readOnly = true)
    public Stadium findStadiumByName(String stadiumName) {
        return stadiumRepository.findStadiumByName(stadiumName);
    }

    /**
     * 스타디움명, boundary, course에 맞는 Food 리스트를 반환한다.
     * @param stadium 스타디움명
     * @param existBoundary 내부 or 외부
     * @param existCourse 식사 or 후식 or null
     * @return 조건에 맞는 Food 리스트
     */
    @Transactional(readOnly = true)
    public List<Food> getFoodsOnCondition(Stadium stadium, Boundary existBoundary, Course existCourse) {
        return stadiumRepository.getFoodsOnCondition(stadium, existBoundary, existCourse);
    }
}
