package kusitms.backend.stadium.domain.model;

import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium extends BaseTimeEntity {

    private Long id;
    private String name;

    public Stadium(
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static Stadium toDomain(
            Long id,
            String name
    ) {
        return new Stadium(id, name);
    }

}
