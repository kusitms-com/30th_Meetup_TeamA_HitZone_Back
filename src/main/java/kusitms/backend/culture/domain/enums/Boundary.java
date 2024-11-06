package kusitms.backend.culture.domain.enums;

import kusitms.backend.culture.status.FoodErrorStatus;
import kusitms.backend.global.exception.CustomException;
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
        throw new CustomException(FoodErrorStatus._BAD_REQUEST_BOUNDARY);
    }
}
