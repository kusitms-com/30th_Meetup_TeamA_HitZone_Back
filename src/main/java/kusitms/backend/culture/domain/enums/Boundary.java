package kusitms.backend.culture.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Boundary {

    INTERIOR("내부"),
    EXTERIOR("외부");

    private final String name;

    public static Optional<Boundary> findByName(String name) {
        return Arrays.stream(Boundary.values())
                .filter(boundary -> boundary.getName().equals(name))
                .findFirst();
    }

}
