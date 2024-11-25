package kusitms.backend.stadium.application.dto.response;

import kusitms.backend.stadium.domain.enums.Boundary;
import kusitms.backend.stadium.domain.model.Entertainment;

import java.util.List;

public record GetEntertainmentsResponseDto(
        List<EntertainmentDto> entertainments
) {
    public record EntertainmentDto(
            String imgUrl,
            Boundary boundary,
            String name,
            List<String> explanations,
            List<String> tips
    ) {
        public static EntertainmentDto from(Entertainment entertainment) {
            return new EntertainmentDto(
                    entertainment.getImgUrl(),
                    entertainment.getBoundary(),
                    entertainment.getName(),
                    entertainment.getExplanations(),
                    entertainment.getTips()
            );
        }
    }

    public static GetEntertainmentsResponseDto of(List<EntertainmentDto> entertainments) {
        return new GetEntertainmentsResponseDto(entertainments);
    }
}
