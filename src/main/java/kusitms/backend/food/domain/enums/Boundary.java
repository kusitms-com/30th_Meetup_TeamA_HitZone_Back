package kusitms.backend.food.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Boundary {

    INTERIOR("내부"),
    EXTERIOR("외부");

    private final String name;

    public static Boundary of(String name) {
        for (Boundary boundary : Boundary.values()) {
            if (boundary.getName().equals(name)) {
                return boundary;
            }
        }
        return null;
    }
}
