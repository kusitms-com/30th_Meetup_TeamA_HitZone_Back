package kusitms.backend.stadium.dto.response;

import kusitms.backend.stadium.domain.entity.Profile;

import java.util.List;

public record GetProfileResponseDto(
        Long profileId,
        String nickname,
        String type,
        String explanation,
        List<String> hashTags
) {
    public static GetProfileResponseDto from(Profile profile) {
        return new GetProfileResponseDto(profile.getId(), profile.getNickname(), profile.getType(), profile.getExplanation(), profile.getHashTags());
    }
}
