package kusitms.backend.culture.dto.response;

import kusitms.backend.culture.domain.entity.Entertainment;
import kusitms.backend.culture.domain.enums.Boundary;

import java.util.List;

public record GetEntertainmentsResponseDto(List<EntertainmentDto> entertainments) {

    public record EntertainmentDto(String imgUrl, Boundary boundary, String name, List<String> explanations, List<String> tips) {
        public static EntertainmentDto from(Entertainment entertainment) {
            return new EntertainmentDto(entertainment.getImgUrl(), entertainment.getBoundary(), entertainment.getName(), entertainment.getExplanations(), entertainment.getTips());
        }
    }

    public static GetEntertainmentsResponseDto of(List<EntertainmentDto> entertainments) {
        return new GetEntertainmentsResponseDto(entertainments);
    }
}
