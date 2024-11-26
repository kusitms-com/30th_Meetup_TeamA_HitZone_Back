package kusitms.backend.user.infra.mapper;

import kusitms.backend.user.domain.model.User;
import kusitms.backend.user.infra.jpa.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity userEntity) {

        if (userEntity == null) {
            return null;
        }

        return User.toDomain(
                userEntity.getId(),
                userEntity.getProvider(),
                userEntity.getProviderId(),
                userEntity.getEmail(),
                userEntity.getNickname()
        );
    }

    public static UserEntity toEntity(User user) {
        return UserEntity.toEntity(
                user.getId(),
                user.getProvider(),
                user.getProviderId(),
                user.getEmail(),
                user.getNickname()
        );
    }
}
