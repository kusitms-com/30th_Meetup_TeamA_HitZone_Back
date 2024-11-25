package kusitms.backend.stadium.domain.model;

import kusitms.backend.stadium.domain.enums.Boundary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Entertainment {

    private Long id;
    private Stadium stadium;
    private String imgUrl;
    private Boundary boundary;
    private String name;
    private List<String> explanations;
    private List<String> tips;

    public Entertainment(
            Long id,
            String imgUrl,
            Boundary boundary,
            String name,
            List<String> explanations,
            List<String> tips
    ) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.boundary = boundary;
        this.name = name;
        this.explanations = explanations;
        this.tips = tips;
    }

    public static Entertainment toDomain(
            Long id,
            String imgUrl,
            Boundary boundary,
            String name,
            List<String> explanations,
            List<String> tips
    ) {
        return new Entertainment(id, imgUrl, boundary, name, explanations, tips);
    }

    public void assignToStadium(Stadium stadium) {
        this.stadium = stadium;
    }
}
