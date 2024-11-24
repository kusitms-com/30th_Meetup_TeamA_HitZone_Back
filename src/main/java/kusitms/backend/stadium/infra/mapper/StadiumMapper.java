package kusitms.backend.stadium.infra.mapper;

import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.infra.jpa.entity.StadiumEntity;

public class StadiumMapper {

    public static Stadium toDomain(StadiumEntity stadiumEntity) {
        return Stadium.toDomain(
                stadiumEntity.getId(),
                stadiumEntity.getName()
        );
    }

    public static StadiumEntity toEntity(Stadium stadium) {
        return StadiumEntity.toEntity(
                stadium.getId(),
                stadium.getName()
        );
    }

}