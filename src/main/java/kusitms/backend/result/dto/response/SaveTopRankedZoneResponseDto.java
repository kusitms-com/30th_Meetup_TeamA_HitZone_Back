package kusitms.backend.result.dto.response;

import lombok.Builder;

@Builder
public record SaveTopRankedZoneResponseDto(Long resultId) {
    public static SaveTopRankedZoneResponseDto from(Long resultId) {
        return new SaveTopRankedZoneResponseDto(resultId);
    }
}
