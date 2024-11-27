package kusitms.backend.stadium.domain.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.domain.enums.StadiumInfo;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StadiumDomainServiceTest {

    private StadiumDomainService stadiumDomainService;

    @BeforeEach
    void setUp() {
        stadiumDomainService = new StadiumDomainService();
    }

    /**
     * 유효한 경기장 이름으로 기본 정보를 가져오는 테스트.
     */
    @Test
    void testGetStadiumInfoByName_Success() {
        // Given
        String stadiumName = "잠실종합운동장 (잠실)";

        // When
        StadiumInfo result = stadiumDomainService.getStadiumInfoByName(stadiumName);

        // Then
        assertNotNull(result);
        assertEquals(StadiumInfo.LG_HOME, result);
    }

    /**
     * 유효하지 않은 경기장 이름으로 예외를 발생시키는 테스트.
     */
    @Test
    void testGetStadiumInfoByName_InvalidName() {
        // Given
        String invalidStadiumName = "없는 경기장";

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () ->
                stadiumDomainService.getStadiumInfoByName(invalidStadiumName));
        assertEquals(StadiumErrorStatus._NOT_FOUND_STADIUM, exception.getErrorCode());
    }

    /**
     * 유효한 경기장 이름으로 상태 타입 배열을 가져오는 테스트.
     */
    @Test
    void testGetStatusTypesByName_Success() {
        // Given
        String stadiumName = "잠실종합운동장 (잠실)";

        // When
        StadiumStatusType[] result = stadiumDomainService.getStatusTypesByName(stadiumName);

        // Then
        assertNotNull(result);
        assertEquals(JamsilStadiumStatusType.values().length, result.length);
    }

    /**
     * 유효하지 않은 경기장 이름으로 상태 타입 배열을 가져올 때 예외를 발생시키는 테스트.
     */
    @Test
    void testGetStatusTypesByName_InvalidName() {
        // Given
        String invalidStadiumName = "없는 경기장";

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () ->
                stadiumDomainService.getStatusTypesByName(invalidStadiumName));
        assertEquals(StadiumErrorStatus._NOT_FOUND_STADIUM, exception.getErrorCode());
    }

    /**
     * 상태 타입 배열에서 유효한 구역 이름과 색상을 가져오는 테스트.
     */
    @Test
    void testGetZonesNameAndColorFromStadium_Success() {
        // Given
        StadiumStatusType[] statusTypes = JamsilStadiumStatusType.values();

        // When
        List<GetStadiumInfosResponseDto.ZoneInfo> result = stadiumDomainService.getZonesNameAndColorFromStadium(statusTypes);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.stream().noneMatch(zoneInfo -> "CHEERING_DUMMY".equals(zoneInfo.zoneName())));
    }

    /**
     * 상태 타입 배열에서 특정 구역 이름을 찾는 테스트.
     */
    @Test
    void testFindZoneInStadium_Success() {
        // Given
        StadiumStatusType[] statusTypes = JamsilStadiumStatusType.values();
        String zoneName = JamsilStadiumStatusType.RED.getZoneName();

        // When
        StadiumStatusType result = stadiumDomainService.findZoneInStadium(statusTypes, zoneName);

        // Then
        assertNotNull(result);
        assertEquals(zoneName, result.getZoneName());
    }

    /**
     * 상태 타입 배열에서 특정 구역 이름이 없을 때 예외를 발생시키는 테스트.
     */
    @Test
    void testFindZoneInStadium_InvalidZoneName() {
        // Given
        StadiumStatusType[] statusTypes = JamsilStadiumStatusType.values();
        String invalidZoneName = "없는 구역";

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () ->
                stadiumDomainService.findZoneInStadium(statusTypes, invalidZoneName));
        assertEquals(StadiumErrorStatus._NOT_FOUND_ZONE, exception.getErrorCode());
    }
}
