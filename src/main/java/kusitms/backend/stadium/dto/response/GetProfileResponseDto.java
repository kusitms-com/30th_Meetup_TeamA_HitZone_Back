package kusitms.backend.stadium.dto.response;

import java.util.List;

public record GetProfileResponseDto(
        Long profileId,
        String nickname,
        String type,
        String explanation,
        List<String> hashTags
){
    public static GetProfileResponseDto of(Long profileId, String nickname, String type, String explanation, List<String> hashTags) {
        return new GetProfileResponseDto(profileId, nickname, type, explanation, hashTags);
    }
}
