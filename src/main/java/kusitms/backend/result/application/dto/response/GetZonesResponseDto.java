package kusitms.backend.result.application.dto.response;

import kusitms.backend.result.domain.model.Zone;
import kusitms.backend.result.domain.value.ReferencesGroup;

import java.util.List;

public record GetZonesResponseDto (
        List<ZoneResponseDto> zones
) {
    public record ZoneResponseDto (
            Long zoneId,
            String name,
            String color,
            List<String> explanations,
            String tip,
            List<ReferencesGroup> referencesGroup
    ) {
        public static ZoneResponseDto from(Zone zone) {
            return new ZoneResponseDto(
                    zone.getId(),
                    zone.getName(),
                    zone.getColor(),
                    zone.getExplanations(),
                    zone.getTip(),
                    zone.getReferencesGroup()
            );
        }
    }
    public static GetZonesResponseDto of(List<ZoneResponseDto> zones) {
        return new GetZonesResponseDto(zones);
    }
}
