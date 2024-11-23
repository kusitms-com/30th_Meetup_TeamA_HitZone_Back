package kusitms.backend.result.domain.repository;

import kusitms.backend.result.domain.model.Profile;
import kusitms.backend.result.domain.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByResult(Result result);
}
