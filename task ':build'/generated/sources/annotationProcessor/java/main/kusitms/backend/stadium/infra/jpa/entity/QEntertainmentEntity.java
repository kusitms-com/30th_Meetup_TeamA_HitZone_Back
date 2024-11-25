package kusitms.backend.stadium.infra.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEntertainmentEntity is a Querydsl query type for EntertainmentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEntertainmentEntity extends EntityPathBase<EntertainmentEntity> {

    private static final long serialVersionUID = -436070736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEntertainmentEntity entertainmentEntity = new QEntertainmentEntity("entertainmentEntity");

    public final kusitms.backend.global.domain.QBaseTimeEntity _super = new kusitms.backend.global.domain.QBaseTimeEntity(this);

    public final EnumPath<kusitms.backend.stadium.domain.enums.Boundary> boundary = createEnum("boundary", kusitms.backend.stadium.domain.enums.Boundary.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<String, StringPath> explanations = this.<String, StringPath>createList("explanations", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath name = createString("name");

    public final QStadiumEntity stadiumEntity;

    public final ListPath<String, StringPath> tips = this.<String, StringPath>createList("tips", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QEntertainmentEntity(String variable) {
        this(EntertainmentEntity.class, forVariable(variable), INITS);
    }

    public QEntertainmentEntity(Path<? extends EntertainmentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEntertainmentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEntertainmentEntity(PathMetadata metadata, PathInits inits) {
        this(EntertainmentEntity.class, metadata, inits);
    }

    public QEntertainmentEntity(Class<? extends EntertainmentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stadiumEntity = inits.isInitialized("stadiumEntity") ? new QStadiumEntity(forProperty("stadiumEntity")) : null;
    }

}

