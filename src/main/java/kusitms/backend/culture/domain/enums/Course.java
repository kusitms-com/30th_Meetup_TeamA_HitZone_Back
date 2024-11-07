package kusitms.backend.culture.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Course {

    MEAL("식사"),
    DESSERT("후식"),
    TOTAL("전체");

    private final String name;

    public static Optional<Course> findByName(String name) {
        return Arrays.stream(Course.values())
                .filter(course -> course.getName().equals(name))
                .findFirst();
    }
}
