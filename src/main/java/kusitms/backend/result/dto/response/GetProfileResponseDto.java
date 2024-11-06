package kusitms.backend.result.dto.response;

import kusitms.backend.result.domain.entity.Profile;

import java.util.List;

public record GetProfileResponseDto(
        Long profileId,
        String imgUrl,
        String nickname,
        String type,
        String explanation,
        List<String> hashTags
) {
    public static GetProfileResponseDto from(Profile profile) {
        return new GetProfileResponseDto(profile.getId(), profile.getImgUrl(), profile.getNickname(), profile.getType(), profile.getExplanation(), profile.getHashTags());
    }
}
