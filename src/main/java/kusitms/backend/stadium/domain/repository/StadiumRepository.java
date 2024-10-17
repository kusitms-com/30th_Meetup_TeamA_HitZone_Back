package kusitms.backend.stadium.domain.repository;

import kusitms.backend.stadium.domain.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {
}
