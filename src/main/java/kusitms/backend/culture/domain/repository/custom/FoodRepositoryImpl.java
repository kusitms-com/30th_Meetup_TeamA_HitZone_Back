package kusitms.backend.culture.domain.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kusitms.backend.culture.domain.entity.Food;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;
import kusitms.backend.stadium.domain.model.Stadium;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kusitms.backend.culture.domain.entity.QFood.food;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Food> findFoodsByConditions(Stadium stadium, Boundary boundary, Course course) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(food.stadium.eq(stadium));
        builder.and(food.boundary.eq(boundary));
        if (boundary == Boundary.INTERIOR) {
            builder.and(food.course.eq(course));
        }

        return jpaQueryFactory
                .selectFrom(food)
                .distinct()
                .where(builder)
                .fetch();
    }
}
