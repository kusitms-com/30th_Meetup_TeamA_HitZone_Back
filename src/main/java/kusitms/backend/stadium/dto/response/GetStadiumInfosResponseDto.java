package kusitms.backend.stadium.dto.response;

import java.util.List;

public record GetStadiumInfosResponseDto(
        String imgUrl,
        String introduction,
        List<ZoneInfo> zones
) {
    public static GetStadiumInfosResponseDto of(String imgUrl, String introduction, List<ZoneInfo> zoneInfos) {
        return new GetStadiumInfosResponseDto(imgUrl, introduction, zoneInfos);
    }

    public record ZoneInfo(
            String zoneName,
            String zoneColor
    ) {
        public static ZoneInfo of(String zoneName, String zoneColor) {
            return new ZoneInfo(zoneName, zoneColor);
        }
    }
}