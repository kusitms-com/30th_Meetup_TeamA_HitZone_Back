package kusitms.backend.result.infra.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfileEntity is a Querydsl query type for ProfileEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfileEntity extends EntityPathBase<ProfileEntity> {

    private static final long serialVersionUID = -37031019L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileEntity profileEntity = new QProfileEntity("profileEntity");

    public final kusitms.backend.global.domain.QBaseTimeEntity _super = new kusitms.backend.global.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<String, StringPath> hashTags = this.<String, StringPath>createList("hashTags", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath nickname = createString("nickname");

    public final QResultEntity resultEntity;

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QProfileEntity(String variable) {
        this(ProfileEntity.class, forVariable(variable), INITS);
    }

    public QProfileEntity(Path<? extends ProfileEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfileEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfileEntity(PathMetadata metadata, PathInits inits) {
        this(ProfileEntity.class, metadata, inits);
    }

    public QProfileEntity(Class<? extends ProfileEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resultEntity = inits.isInitialized("resultEntity") ? new QResultEntity(forProperty("resultEntity"), inits.get("resultEntity")) : null;
    }

}

