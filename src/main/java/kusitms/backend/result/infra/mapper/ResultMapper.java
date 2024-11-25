package kusitms.backend.result.infra.mapper;

import kusitms.backend.result.domain.model.Profile;
import kusitms.backend.result.domain.model.Result;
import kusitms.backend.result.domain.model.Zone;
import kusitms.backend.result.infra.jpa.entity.ProfileEntity;
import kusitms.backend.result.infra.jpa.entity.ResultEntity;
import kusitms.backend.result.infra.jpa.entity.ZoneEntity;

import java.util.List;

public class ResultMapper {

    public static Result toDomain(ResultEntity resultEntity) {

        Profile profile = ProfileMapper.toDomain(resultEntity.getProfileEntity());
        List<Zone> zones = resultEntity.getZoneEntities().stream()
                .map(ZoneMapper::toDomain)
                .toList();

        Result result = Result.toDomain(
                resultEntity.getId(),
                resultEntity.getUserId(),
                resultEntity.getStadiumId(),
                resultEntity.getPreference()
        );

        result.addProfile(profile);
        zones.forEach(result::addZone);
        return result;
    }

    public static ResultEntity toEntity(Result result) {

        ProfileEntity profileEntity = ProfileMapper.toEntity(result.getProfile());
        List<ZoneEntity> zoneEntities = result.getZones().stream()
                .map(ZoneMapper::toEntity)
                .toList();

        ResultEntity resultEntity = ResultEntity.toEntity(
                result.getId(),
                result.getUserId(),
                result.getStadiumId(),
                result.getPreference()
        );

        resultEntity.addProfileEntity(profileEntity);
        zoneEntities.forEach(resultEntity::addZoneEntity);
        return resultEntity;
    }
}
