package kusitms.backend.result.domain.model;

import kusitms.backend.result.domain.value.ReferencesGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Zone {

    private Long id;
    private Result result;
    private String name;
    private String color;
    private List<String> explanations;
    private String tip;
    private List<ReferencesGroup> referencesGroup;

    public Zone(
            Long id,
            String name,
            String color,
            List<String> explanations,
            String tip,
            List<ReferencesGroup> referencesGroup
    ) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.explanations = explanations;
        this.tip = tip;
        this.referencesGroup = referencesGroup;
    }

    public static Zone toDomain(
            Long id,
            String name,
            String color,
            List<String> explanations,
            String tip,
            List<ReferencesGroup> referencesGroup
    ) {
        return new Zone(id, name, color, explanations, tip, referencesGroup);
    }

    public void assignToResult(Result result) {
        this.result = result;
    }
}
