package kusitms.backend.culture.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Boundary {

    INTERIOR("내부"),
    EXTERIOR("외부");

    private final String name;

    public static boolean isExists(String name) {
        return Arrays.stream(Boundary.values())
                .anyMatch(boundary -> boundary.getName().equals(name));
    }

    public static Boundary of(String name) {
        for (Boundary boundary : Boundary.values()) {
            if (boundary.getName().equals(name)) {
                return boundary;
            }
        }
        return null;
    }
}
