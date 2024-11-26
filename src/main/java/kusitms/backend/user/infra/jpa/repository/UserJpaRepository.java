package kusitms.backend.user.infra.jpa.repository;

import kusitms.backend.user.infra.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByProviderId(String providerId);
    UserEntity findByNickname(String nickname);
//    UserEntity findByPhoneNumber(String phoneNumber);
}
