package kusitms.backend.user.domain.repository;

import kusitms.backend.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByProviderId(String providerId);
    User findByNickname(String nickname);
//    User findByPhoneNumber(String phoneNumber);
}
