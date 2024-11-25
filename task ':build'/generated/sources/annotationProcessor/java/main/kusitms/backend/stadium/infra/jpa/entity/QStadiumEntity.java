package kusitms.backend.stadium.infra.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStadiumEntity is a Querydsl query type for StadiumEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStadiumEntity extends EntityPathBase<StadiumEntity> {

    private static final long serialVersionUID = -1193580763L;

    public static final QStadiumEntity stadiumEntity = new QStadiumEntity("stadiumEntity");

    public final kusitms.backend.global.domain.QBaseTimeEntity _super = new kusitms.backend.global.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<EntertainmentEntity, QEntertainmentEntity> entertainmentEntities = this.<EntertainmentEntity, QEntertainmentEntity>createList("entertainmentEntities", EntertainmentEntity.class, QEntertainmentEntity.class, PathInits.DIRECT2);

    public final ListPath<FoodEntity, QFoodEntity> foodEntities = this.<FoodEntity, QFoodEntity>createList("foodEntities", FoodEntity.class, QFoodEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QStadiumEntity(String variable) {
        super(StadiumEntity.class, forVariable(variable));
    }

    public QStadiumEntity(Path<? extends StadiumEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStadiumEntity(PathMetadata metadata) {
        super(StadiumEntity.class, metadata);
    }

}

