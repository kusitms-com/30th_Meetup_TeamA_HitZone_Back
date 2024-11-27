package kusitms.backend.result.infra.mapper;

import kusitms.backend.result.domain.model.Profile;
import kusitms.backend.result.infra.jpa.entity.ProfileEntity;

public class ProfileMapper {

    public static Profile toDomain(ProfileEntity profileEntity) {
        return Profile.toDomain(
                profileEntity.getId(),
                profileEntity.getImgUrl(),
                profileEntity.getNickname(),
                profileEntity.getType(),
                profileEntity.getHashTags()
        );
    }

    public static ProfileEntity toEntity(Profile profile) {
        return ProfileEntity.toEntity(
                profile.getId(),
                profile.getImgUrl(),
                profile.getNickname(),
                profile.getType(),
                profile.getHashTags()
        );
    }
}
