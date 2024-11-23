package kusitms.backend.result.domain.repository;

import kusitms.backend.result.domain.model.Result;
import kusitms.backend.result.domain.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findAllByResult(Result result);
}
