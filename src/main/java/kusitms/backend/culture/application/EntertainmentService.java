package kusitms.backend.culture.application;

import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.repository.EntertainmentRepository;
import kusitms.backend.culture.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.culture.status.FoodErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.infra.jpa.repository.StadiumJpaRepository;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntertainmentService {

    private final EntertainmentRepository entertainmentRepository;
    private final StadiumJpaRepository stadiumRepository;

    @Transactional(readOnly = true)
    public GetEntertainmentsResponseDto getSuitableEntertainments(String stadiumName, String boundary) {
        Stadium stadium = stadiumRepository.findByName(stadiumName)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));

        Boundary existBoundary = Boundary.findByName(boundary)
                .orElseThrow(() -> new CustomException(FoodErrorStatus._BAD_REQUEST_BOUNDARY));

        List<GetEntertainmentsResponseDto.EntertainmentDto> entertainments = entertainmentRepository.findAllByStadiumAndBoundary(stadium, existBoundary)
                .stream()
                .map(GetEntertainmentsResponseDto.EntertainmentDto::from)
                .toList();
        return GetEntertainmentsResponseDto.of(entertainments);

    }
}
