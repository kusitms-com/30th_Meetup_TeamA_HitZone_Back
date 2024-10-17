package kusitms.backend.stadium.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record SaveTopRankedZoneResponseDto(
        Long resultId
) {
    public static SaveTopRankedZoneResponseDto from(Long resultId) {
        return new SaveTopRankedZoneResponseDto(resultId);
    }
}
