package kusitms.backend.stadium.dto.response;

import java.util.List;

public record GetZonesNameResponseDto(
        List<String> names
) {
    public static GetZonesNameResponseDto of(List<String> names) {
        return new GetZonesNameResponseDto(names);
    }
}
