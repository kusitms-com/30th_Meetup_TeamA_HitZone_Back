package kusitms.backend.culture.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Course {

    MEAL("식사"),
    DESSERT("후식"),
    TOTAL("전체");

    private final String name;

    public static boolean isExists(String name) {
        return Arrays.stream(Course.values())
                .anyMatch(course -> course.getName().equals(name));
    }

    public static Course of(String name) {
        for (Course course : Course.values()) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }
}
