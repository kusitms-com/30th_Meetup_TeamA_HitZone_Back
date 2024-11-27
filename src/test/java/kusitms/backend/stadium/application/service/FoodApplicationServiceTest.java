package kusitms.backend.stadium.application.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.FoodApplicationService;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.application.dto.response.GetFoodsResponseDto;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.status.FoodErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodApplicationServiceTest {

    @InjectMocks
    private FoodApplicationService service;

    @Mock
    private StadiumApplicationService stadiumApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 정상적인 입력으로 조건에 맞는 음식 리스트를 성공적으로 가져오는 테스트.
     */
    @Test
    void testGetFoodsOnCondition_Success() {
        // Given
        String stadiumName = "잠실종합운동장";
        String boundary = "내부";
        String course = "식사";
        Stadium stadium = mock(Stadium.class);
        when(stadiumApplicationService.findStadiumByName(stadiumName)).thenReturn(stadium);
        when(stadiumApplicationService.getFoodsOnCondition(any(), any(), any())).thenReturn(List.of());

        // When
        GetFoodsResponseDto result = service.getFoodsOnCondition(stadiumName, boundary, course);

        // Then
        assertNotNull(result);
        assertEquals(0, result.foods().size());
    }

    /**
     * 잘못된 boundary 값으로 예외가 발생하는 테스트.
     */
    @Test
    void testGetFoodsOnCondition_InvalidBoundary() {
        // Given
        String stadiumName = "잠실종합운동장";
        String boundary = "잘못된값";
        String course = "식사";

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () ->
                service.getFoodsOnCondition(stadiumName, boundary, course));
        assertEquals(FoodErrorStatus._BAD_REQUEST_BOUNDARY, exception.getErrorCode());
    }

    /**
     * 잘못된 course 값으로 예외가 발생하는 테스트.
     */
    @Test
    void testGetFoodsOnCondition_InvalidCourse() {
        // Given
        String stadiumName = "잠실종합운동장";
        String boundary = "내부";
        String course = "잘못된값";

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () ->
                service.getFoodsOnCondition(stadiumName, boundary, course));
        assertEquals(FoodErrorStatus._BAD_REQUEST_COURSE, exception.getErrorCode());
    }
}
