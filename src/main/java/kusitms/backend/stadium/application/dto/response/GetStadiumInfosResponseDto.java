package kusitms.backend.stadium.application.dto.response;

import java.util.List;

public record GetStadiumInfosResponseDto(
        String imgUrl,
        String firstBaseSide,
        String thirdBaseSide,
        List<ZoneInfo> zones
) {
    public static GetStadiumInfosResponseDto of(String imgUrl, String firstBaseSide, String thirdBaseSideList, List<ZoneInfo> zoneInfos) {
        return new GetStadiumInfosResponseDto(imgUrl, firstBaseSide, thirdBaseSideList, zoneInfos);
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