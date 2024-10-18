package kusitms.backend.stadium.dto.response;

import kusitms.backend.stadium.common.ReferencesGroup;
import kusitms.backend.stadium.domain.entity.Zone;

import java.util.List;

public record GetZonesResponseDto (List<ZoneResponseDto> zones) {

    public record ZoneResponseDto (
            Long zoneId,
            String name,
            List<String> explanations,
            String tip,
            List<ReferencesGroup> referencesGroup
    ) {
        public static ZoneResponseDto from(Zone zone) {
            return new ZoneResponseDto(zone.getId(), zone.getName(), zone.getExplanations(), zone.getTip(), zone.getReferencesGroup());
        }
    }

    public static GetZonesResponseDto from(List<ZoneResponseDto> zones) {
        return new GetZonesResponseDto(zones);
    }
}
