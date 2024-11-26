package kusitms.backend.result.infra.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZoneEntity is a Querydsl query type for ZoneEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QZoneEntity extends EntityPathBase<ZoneEntity> {

    private static final long serialVersionUID = 85634726L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZoneEntity zoneEntity = new QZoneEntity("zoneEntity");

    public final kusitms.backend.global.domain.QBaseTimeEntity _super = new kusitms.backend.global.domain.QBaseTimeEntity(this);

    public final StringPath color = createString("color");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<String, StringPath> explanations = this.<String, StringPath>createList("explanations", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<kusitms.backend.result.domain.value.ReferencesGroup, SimplePath<kusitms.backend.result.domain.value.ReferencesGroup>> referencesGroup = this.<kusitms.backend.result.domain.value.ReferencesGroup, SimplePath<kusitms.backend.result.domain.value.ReferencesGroup>>createList("referencesGroup", kusitms.backend.result.domain.value.ReferencesGroup.class, SimplePath.class, PathInits.DIRECT2);

    public final QResultEntity resultEntity;

    public final StringPath tip = createString("tip");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QZoneEntity(String variable) {
        this(ZoneEntity.class, forVariable(variable), INITS);
    }

    public QZoneEntity(Path<? extends ZoneEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZoneEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZoneEntity(PathMetadata metadata, PathInits inits) {
        this(ZoneEntity.class, metadata, inits);
    }

    public QZoneEntity(Class<? extends ZoneEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resultEntity = inits.isInitialized("resultEntity") ? new QResultEntity(forProperty("resultEntity"), inits.get("resultEntity")) : null;
    }

}

