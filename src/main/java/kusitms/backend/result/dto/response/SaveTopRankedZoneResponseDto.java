package kusitms.backend.result.dto.response;

import lombok.Builder;

@Builder
public record SaveTopRankedZoneResponseDto(
        Long resultId
) {
    public static SaveTopRankedZoneResponseDto of(Long resultId) {
        return new SaveTopRankedZoneResponseDto(resultId);
    }
}
