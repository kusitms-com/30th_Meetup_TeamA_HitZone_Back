package kusitms.backend.result.infra.mapper;

import kusitms.backend.result.domain.model.Zone;
import kusitms.backend.result.infra.jpa.entity.ZoneEntity;

public class ZoneMapper {

    public static Zone toDomain(ZoneEntity zoneEntity) {

        if (zoneEntity == null) {
            return null;
        }

        return Zone.toDomain(
                zoneEntity.getId(),
                zoneEntity.getName(),
                zoneEntity.getColor(),
                zoneEntity.getExplanations(),
                zoneEntity.getTip(),
                zoneEntity.getReferencesGroup()
        );
    }

    public static ZoneEntity toEntity(Zone zone) {
        return ZoneEntity.toEntity(
                zone.getId(),
                zone.getName(),
                zone.getColor(),
                zone.getExplanations(),
                zone.getTip(),
                zone.getReferencesGroup()
        );
    }
}
