package kusitms.backend.food.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Course {

    MEAL("식사"),
    DESSERT("후식");

    private final String name;

    public static Course of(String name) {
        for (Course course : Course.values()) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }
}
