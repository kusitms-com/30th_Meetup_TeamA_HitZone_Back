package kusitms.backend.result.application.dto.response;

import lombok.Builder;

@Builder
public record SaveTopRankedZoneResponseDto(
        Long resultId
) {
    public static SaveTopRankedZoneResponseDto of(Long resultId) {
        return new SaveTopRankedZoneResponseDto(resultId);
    }
}
