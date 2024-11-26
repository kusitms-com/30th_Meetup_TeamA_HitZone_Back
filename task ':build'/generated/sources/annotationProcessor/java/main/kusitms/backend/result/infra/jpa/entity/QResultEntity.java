package kusitms.backend.result.infra.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResultEntity is a Querydsl query type for ResultEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResultEntity extends EntityPathBase<ResultEntity> {

    private static final long serialVersionUID = -450275177L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResultEntity resultEntity = new QResultEntity("resultEntity");

    public final kusitms.backend.global.domain.QBaseTimeEntity _super = new kusitms.backend.global.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath preference = createString("preference");

    public final QProfileEntity profileEntity;

    public final NumberPath<Long> stadiumId = createNumber("stadiumId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final ListPath<ZoneEntity, QZoneEntity> zoneEntities = this.<ZoneEntity, QZoneEntity>createList("zoneEntities", ZoneEntity.class, QZoneEntity.class, PathInits.DIRECT2);

    public QResultEntity(String variable) {
        this(ResultEntity.class, forVariable(variable), INITS);
    }

    public QResultEntity(Path<? extends ResultEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResultEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResultEntity(PathMetadata metadata, PathInits inits) {
        this(ResultEntity.class, metadata, inits);
    }

    public QResultEntity(Class<? extends ResultEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profileEntity = inits.isInitialized("profileEntity") ? new QProfileEntity(forProperty("profileEntity"), inits.get("profileEntity")) : null;
    }

}

