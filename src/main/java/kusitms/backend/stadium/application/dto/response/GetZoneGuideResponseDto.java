package kusitms.backend.stadium.application.dto.response;


import kusitms.backend.result.domain.value.ReferencesGroup;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;

import java.util.List;

public record GetZoneGuideResponseDto(
        String imgUrl,
        String zoneName,
        String zoneColor,
        String explanation,
        String firstBaseSide,
        String thirdBaseSide,
        String[] entrance,
        String[] stepSpacing,
        String[] seatSpacing,
        String[] usageInformation,
        String tip,
        List<ReferencesGroup> referencesGroup
) {
    public static GetZoneGuideResponseDto from(StadiumStatusType zone) {
        return new GetZoneGuideResponseDto(
                zone.getImgUrl(),
                zone.getZoneName(),
                zone.getZoneColor(),
                zone.getOneLineDescription(),
                zone.getFirstBaseSide(),
                zone.getThirdBaseSide(),
                zone.getEntrance(),
                zone.getStepSpacing(),
                zone.getSeatSpacing(),
                zone.getUsageInformation(),
                zone.getTip(),
                zone.getReferencesGroup()
        );
    }
}
