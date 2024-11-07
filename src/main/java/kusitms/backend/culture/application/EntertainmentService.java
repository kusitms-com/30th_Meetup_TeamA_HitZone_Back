package kusitms.backend.culture.application;

import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.repository.EntertainmentRepository;
import kusitms.backend.culture.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntertainmentService {

    private final EntertainmentRepository entertainmentRepository;
    private final StadiumRepository stadiumRepository;

    @Transactional(readOnly = true)
    public GetEntertainmentsResponseDto getSuitableEntertainments(String stadiumName, String boundary) {
        Stadium stadium = stadiumRepository.findByName(stadiumName)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));
        Boundary existBoundary = Boundary.of(boundary);

        List<GetEntertainmentsResponseDto.EntertainmentDto> entertainments = entertainmentRepository.findAllByStadiumAndBoundary(stadium, existBoundary)
                .stream()
                .map(GetEntertainmentsResponseDto.EntertainmentDto::from)
                .toList();
        return GetEntertainmentsResponseDto.of(entertainments);

    }
}
