package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.application.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.status.EntertainmentErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntertainmentApplicationService {

    private final StadiumApplicationService stadiumApplicationService;

    /**
     * 해당 스타디움의 boundary의 즐길거리 리스트를 반환한다.
     * @param stadiumName 스타디움명
     * @param boundary 위치 (내부 or 외부)
     * @return 즐길거리 리스트 (이미지Url, boundary, 이름, 설명 리스트, 팁 리스트)
     */
    @Transactional(readOnly = true)
    public GetEntertainmentsResponseDto getSuitableEntertainments(String stadiumName, String boundary) {
        Stadium stadium = stadiumApplicationService.findStadiumByName(stadiumName);
        log.info("The Corresponding stadium Id is " + stadium.getEntertainments());
        Boundary existBoundary = Boundary.findByName(boundary)
                .orElseThrow(() -> new CustomException(EntertainmentErrorStatus._BAD_REQUEST_BOUNDARY));

        List<GetEntertainmentsResponseDto.EntertainmentDto> entertainments = stadium.getEntertainments()
                .stream()
                .filter(entertainment -> entertainment.getBoundary().equals(existBoundary))
                .map(GetEntertainmentsResponseDto.EntertainmentDto::from)
                .toList();
        log.info("조건에 해당하는 즐길거리 목록이 조회되었습니다.");
        return GetEntertainmentsResponseDto.of(entertainments);
    }
}
