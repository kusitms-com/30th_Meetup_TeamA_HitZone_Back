package kusitms.backend.result.domain.repository;

import kusitms.backend.result.domain.entity.Profile;
import kusitms.backend.result.domain.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByResult(Result result);
}
