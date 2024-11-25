package kusitms.backend.stadium.infra.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodEntity is a Querydsl query type for FoodEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodEntity extends EntityPathBase<FoodEntity> {

    private static final long serialVersionUID = -1884503268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodEntity foodEntity = new QFoodEntity("foodEntity");

    public final kusitms.backend.global.domain.QBaseTimeEntity _super = new kusitms.backend.global.domain.QBaseTimeEntity(this);

    public final EnumPath<kusitms.backend.stadium.domain.enums.Boundary> boundary = createEnum("boundary", kusitms.backend.stadium.domain.enums.Boundary.class);

    public final EnumPath<kusitms.backend.stadium.domain.enums.Course> course = createEnum("course", kusitms.backend.stadium.domain.enums.Course.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath location = createString("location");

    public final ListPath<String, StringPath> menu = this.<String, StringPath>createList("menu", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath price = createString("price");

    public final QStadiumEntity stadiumEntity;

    public final StringPath tip = createString("tip");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QFoodEntity(String variable) {
        this(FoodEntity.class, forVariable(variable), INITS);
    }

    public QFoodEntity(Path<? extends FoodEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodEntity(PathMetadata metadata, PathInits inits) {
        this(FoodEntity.class, metadata, inits);
    }

    public QFoodEntity(Class<? extends FoodEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stadiumEntity = inits.isInitialized("stadiumEntity") ? new QStadiumEntity(forProperty("stadiumEntity")) : null;
    }

}

