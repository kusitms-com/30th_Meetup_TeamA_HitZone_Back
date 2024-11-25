package kusitms.backend.result.application.dto.response;

import kusitms.backend.result.domain.model.Profile;

import java.util.List;

public record GetProfileResponseDto(
        Long profileId,
        String imgUrl,
        String nickname,
        String type,
        List<String> hashTags
) {
    public static GetProfileResponseDto from(Profile profile) {
        return new GetProfileResponseDto(
                profile.getId(),
                profile.getImgUrl(),
                profile.getNickname(),
                profile.getType(),
                profile.getHashTags()
        );
    }
}
