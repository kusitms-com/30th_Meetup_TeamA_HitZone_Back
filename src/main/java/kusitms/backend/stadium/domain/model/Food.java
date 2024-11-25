package kusitms.backend.stadium.domain.model;

import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.enums.Course;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    private Long id;
    private Stadium stadium;
    private String imgUrl;
    private Boundary boundary;
    private Course course;
    private String name;
    private String location;
    private List<String> menu;
    private String price;
    private String tip;

    public Food(
            Long id,
            String imgUrl,
            Boundary boundary,
            Course course,
            String name,
            String location,
            List<String> menu,
            String price,
            String tip
    ) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.boundary = boundary;
        this.course = course;
        this.name = name;
        this.location = location;
        this.menu = menu;
        this.price = price;
        this.tip = tip;
    }

    public static Food toDomain(
            Long id,
            String imgUrl,
            Boundary boundary,
            Course course,
            String name,
            String location,
            List<String> menu,
            String price,
            String tip
    ) {
        return new Food(id, imgUrl, boundary, course, name, location, menu, price, tip);
    }

    public void assignToStadium(Stadium stadium) {
        this.stadium = stadium;
    }
}
