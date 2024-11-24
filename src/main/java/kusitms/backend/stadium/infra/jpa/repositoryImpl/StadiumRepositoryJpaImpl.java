package kusitms.backend.stadium.infra.jpa.repositoryImpl;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.infra.jpa.repository.StadiumJpaRepository;
import kusitms.backend.stadium.infra.mapper.StadiumMapper;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StadiumRepositoryJpaImpl implements StadiumRepository {

    private final StadiumJpaRepository stadiumJpaRepository;

    @Override
    public Stadium findStadiumByName(String stadiumName) {
        return stadiumJpaRepository.findByName(stadiumName)
                .map(StadiumMapper::toDomain)
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));
    }
}
