package kusitms.backend.stadium.application.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.domain.enums.StadiumInfo;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.domain.service.StadiumDomainService;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StadiumApplicationServiceTest {

    @InjectMocks
    private StadiumApplicationService stadiumApplicationService;

    @Mock
    private StadiumDomainService stadiumDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 정상적인 경기장 이름으로 경기장 정보를 가져오는 테스트.
     */
    @Test
    void testGetStadiumInfos_Success() {
        // Given
        String stadiumName = "잠실종합운동장";
        StadiumInfo stadiumInfo = StadiumInfo.LG_HOME;
        StadiumStatusType[] stadiumStatusTypes = new StadiumStatusType[0];
        List<GetStadiumInfosResponseDto.ZoneInfo> zoneInfos = List.of();

        when(stadiumDomainService.getStadiumInfoByName(stadiumName)).thenReturn(stadiumInfo);
        when(stadiumDomainService.getStatusTypesByName(stadiumName)).thenReturn(stadiumStatusTypes);
        when(stadiumDomainService.getZonesNameAndColorFromStadium(stadiumStatusTypes)).thenReturn(zoneInfos);

        // When
        GetStadiumInfosResponseDto result = stadiumApplicationService.getStadiumInfos(stadiumName);

        // Then
        assertNotNull(result);
        assertEquals(stadiumInfo.getImgUrl(), result.imgUrl());
        assertEquals(0, result.zones().size());
    }

    /**
     * 잘못된 경기장 이름으로 예외를 발생시키는 테스트.
     */
    @Test
    void testGetStadiumInfos_InvalidStadiumName() {
        // Given
        String invalidStadiumName = "잘못된경기장";

        when(stadiumDomainService.getStadiumInfoByName(invalidStadiumName))
                .thenThrow(new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () ->
                stadiumApplicationService.getStadiumInfos(invalidStadiumName));
        assertEquals(StadiumErrorStatus._NOT_FOUND_STADIUM, exception.getErrorCode());
    }
}
