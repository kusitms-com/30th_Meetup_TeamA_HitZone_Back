package kusitms.backend.stadium.domain.repository;

import kusitms.backend.stadium.domain.entity.Result;
import kusitms.backend.stadium.domain.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findAllByResult(Result result);
}
