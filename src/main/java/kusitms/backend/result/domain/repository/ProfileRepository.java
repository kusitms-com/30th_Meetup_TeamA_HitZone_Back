package kusitms.backend.result.domain.repository;

import kusitms.backend.result.domain.entity.Profile;
import kusitms.backend.result.domain.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByResult(Result result);
}
