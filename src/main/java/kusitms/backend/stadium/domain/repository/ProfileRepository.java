package kusitms.backend.stadium.domain.repository;

import kusitms.backend.stadium.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
