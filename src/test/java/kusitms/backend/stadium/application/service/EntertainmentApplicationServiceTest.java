package kusitms.backend.stadium.application.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.EntertainmentApplicationService;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.application.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.status.EntertainmentErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EntertainmentApplicationServiceTest {

    @InjectMocks
    private EntertainmentApplicationService service;

    @Mock
    private StadiumApplicationService stadiumApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 정상적인 스타디움 이름과 boundary 값으로 즐길거리 리스트를 성공적으로 가져오는 테스트.
     */
    @Test
    void testGetSuitableEntertainments_Success() {
        // Given
        String stadiumName = "잠실종합운동장";
        String boundary = "내부";
        Stadium stadium = mock(Stadium.class);
        when(stadiumApplicationService.findStadiumByName(stadiumName)).thenReturn(stadium);
        when(stadium.getEntertainments()).thenReturn(List.of());

        // When
        GetEntertainmentsResponseDto result = service.getSuitableEntertainments(stadiumName, boundary);

        // Then
        assertNotNull(result);
        assertEquals(0, result.entertainments().size());
    }

    /**
     * 유효하지 않은 boundary 값으로 예외를 발생시키는 테스트.
     */
    @Test
    void testGetSuitableEntertainments_InvalidBoundary() {
        // Given
        String stadiumName = "잠실종합운동장";
        String invalidBoundary = "잘못된값";
        Stadium stadium = mock(Stadium.class);

        // When
        when(stadiumApplicationService.findStadiumByName(stadiumName)).thenReturn(stadium);

        // Then
        CustomException exception = assertThrows(CustomException.class, () ->
                service.getSuitableEntertainments(stadiumName, invalidBoundary));
        assertEquals(EntertainmentErrorStatus._BAD_REQUEST_BOUNDARY, exception.getErrorCode());
    }
}
