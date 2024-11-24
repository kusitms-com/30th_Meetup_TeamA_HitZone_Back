package kusitms.backend.stadium.infra.jpa.repository;

import kusitms.backend.stadium.infra.jpa.entity.StadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StadiumJpaRepository extends JpaRepository<StadiumEntity, Long> {
    Optional<StadiumEntity> findByName(String stadium);
}
