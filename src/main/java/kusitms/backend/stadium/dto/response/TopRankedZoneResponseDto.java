package kusitms.backend.stadium.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record TopRankedZoneResponseDto(
        String stadium,
        String preference,
        Map<String, Object> recommendedProfile,
        List<Map<String, Object>> recommendZones) {
}
