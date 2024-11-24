package kusitms.backend.stadium.domain.repository;

import kusitms.backend.stadium.domain.model.Stadium;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository {
    Stadium findStadiumByName(String stadiumName);
}
