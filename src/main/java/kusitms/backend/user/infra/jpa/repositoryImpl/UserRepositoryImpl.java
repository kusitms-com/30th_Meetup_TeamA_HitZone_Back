package kusitms.backend.user.infra.jpa.repositoryImpl;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.user.domain.model.User;
import kusitms.backend.user.domain.repository.UserRepository;
import kusitms.backend.user.infra.jpa.entity.UserEntity;
import kusitms.backend.user.infra.jpa.repository.UserJpaRepository;
import kusitms.backend.user.infra.mapper.UserMapper;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findUserById(Long id) {
        UserEntity userEntity = userJpaRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorStatus._NOT_FOUND_USER));
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public User findUserByProviderId(String providerId) {
        UserEntity userEntity = userJpaRepository.findByProviderId(providerId);
        if (userEntity == null) {
            return null;
        }
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public User findUserByNickname(String nickname) {
        UserEntity userEntity = userJpaRepository.findByNickname(nickname);
        if (userEntity == null) {
            return null;
        }
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userJpaRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(userEntity);
    }
}
