package kusitms.backend.culture.domain.repository;

import kusitms.backend.culture.domain.entity.Entertainment;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.stadium.domain.model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntertainmentRepository extends JpaRepository<Entertainment, Long> {
    List<Entertainment> findAllByStadiumAndBoundary(Stadium stadium, Boundary boundary);
}
