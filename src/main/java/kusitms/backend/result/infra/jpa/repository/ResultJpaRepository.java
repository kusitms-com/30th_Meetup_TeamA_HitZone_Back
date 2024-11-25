package kusitms.backend.result.infra.jpa.repository;

import kusitms.backend.result.infra.jpa.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultJpaRepository extends JpaRepository<ResultEntity, Long> {
}
