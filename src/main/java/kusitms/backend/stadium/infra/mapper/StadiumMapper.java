package kusitms.backend.stadium.infra.mapper;

import kusitms.backend.stadium.domain.model.Entertainment;
import kusitms.backend.stadium.domain.model.Food;
import kusitms.backend.stadium.domain.model.Stadium;
import kusitms.backend.stadium.infra.jpa.entity.EntertainmentEntity;
import kusitms.backend.stadium.infra.jpa.entity.FoodEntity;
import kusitms.backend.stadium.infra.jpa.entity.StadiumEntity;

import java.util.List;

public class StadiumMapper {

    public static Stadium toDomain(StadiumEntity stadiumEntity) {

        List<Food> foods = stadiumEntity.getFoodEntities().stream()
                .map(FoodMapper::toDomain)
                .toList();

        List<Entertainment> entertainments = stadiumEntity.getEntertainmentEntities().stream()
                .map(EntertainmentMapper::toDomain)
                .toList();

        Stadium stadium = Stadium.toDomain(
                stadiumEntity.getId(),
                stadiumEntity.getName()
        );

        foods.forEach(stadium::addFood);
        entertainments.forEach(stadium::addEntertainment);
        return stadium;
    }

    public static StadiumEntity toEntity(Stadium stadium) {

        List<FoodEntity> foodEntities = stadium.getFoods().stream()
                .map(FoodMapper::toEntity)
                .toList();

        List<EntertainmentEntity> entertainmentEntities = stadium.getEntertainments().stream()
                .map(EntertainmentMapper::toEntity)
                .toList();

        StadiumEntity stadiumEntity = StadiumEntity.toEntity(
                stadium.getId(),
                stadium.getName()
        );

        foodEntities.forEach(stadiumEntity::addFoodEntity);
        entertainmentEntities.forEach(stadiumEntity::addEntertainmentEntity);
        return stadiumEntity;
    }
}
