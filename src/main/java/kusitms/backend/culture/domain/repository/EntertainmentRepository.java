package kusitms.backend.culture.domain.repository;

import kusitms.backend.culture.domain.entity.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntertainmentRepository extends JpaRepository<Entertainment, Long> {
}
