package kusitms.backend.result.domain.repository;

import kusitms.backend.result.domain.model.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository {
    Long saveResult(Result result);
    Result findResultById(Long id);
}
