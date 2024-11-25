package kusitms.backend.stadium.infra.mapper;


import kusitms.backend.stadium.domain.model.Entertainment;
import kusitms.backend.stadium.infra.jpa.entity.EntertainmentEntity;

public class EntertainmentMapper {

    public static Entertainment toDomain(EntertainmentEntity entertainmentEntity) {
        return Entertainment.toDomain(
                entertainmentEntity.getId(),
                entertainmentEntity.getImgUrl(),
                entertainmentEntity.getBoundary(),
                entertainmentEntity.getName(),
                entertainmentEntity.getExplanations(),
                entertainmentEntity.getTips()
        );
    }

    public static EntertainmentEntity toEntity(Entertainment entertainment) {
        return EntertainmentEntity.toEntity(
                entertainment.getId(),
                entertainment.getImgUrl(),
                entertainment.getBoundary(),
                entertainment.getName(),
                entertainment.getExplanations(),
                entertainment.getTips()
        );
    }
}
