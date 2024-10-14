package kusitms.backend.stadium.dto.request;

import lombok.Builder;

@Builder
public record TopRankedZoneRequestDto(String stadium, String preference, String[] clientKeywords) {
}
