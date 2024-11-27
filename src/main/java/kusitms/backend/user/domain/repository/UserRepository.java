package kusitms.backend.user.domain.repository;

import kusitms.backend.user.domain.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findUserById(Long id);
    User findUserByProviderId(String providerId);
    User findUserByNickname(String nickname);
    User saveUser(User user);
//    User findUserByPhoneNumber(String phoneNumber);
}
