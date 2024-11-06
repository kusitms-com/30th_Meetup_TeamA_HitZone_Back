package kusitms.backend.culture.domain.enums;

import kusitms.backend.culture.status.FoodErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Course {

    MEAL("식사"),
    DESSERT("후식"),
    TOTAL("전체");

    private final String name;

    public static Course of(String name) {
        for (Course course : Course.values()) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        throw new CustomException(FoodErrorStatus._BAD_REQUEST_COURSE);
    }
}
