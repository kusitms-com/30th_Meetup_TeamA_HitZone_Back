package kusitms.backend.stadium.infra.jpa.repositoryImpl.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.infra.jpa.entity.FoodEntity;
import kusitms.backend.stadium.infra.jpa.repository.custom.StadiumRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kusitms.backend.stadium.infra.jpa.entity.QFoodEntity.foodEntity;

@Repository
@RequiredArgsConstructor
public class StadiumRepositoryImpl implements StadiumRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FoodEntity> findFoodsByConditions(Stadium stadium, Boundary boundary, Course course) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(foodEntity.stadiumEntity.id.eq(stadium.getId()));
        builder.and(foodEntity.boundary.eq(boundary));
        if (boundary == Boundary.INTERIOR) {
            builder.and(foodEntity.course.eq(course));
        }

        return jpaQueryFactory
                .selectFrom(foodEntity)
                .distinct()
                .where(builder)
                .fetch();
    }
}
