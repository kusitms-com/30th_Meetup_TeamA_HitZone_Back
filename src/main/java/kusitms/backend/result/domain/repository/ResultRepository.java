package kusitms.backend.result.domain.repository;

import kusitms.backend.result.domain.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
