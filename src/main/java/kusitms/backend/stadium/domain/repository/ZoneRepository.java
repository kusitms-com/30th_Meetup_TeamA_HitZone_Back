package kusitms.backend.stadium.domain.repository;

import kusitms.backend.stadium.domain.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
