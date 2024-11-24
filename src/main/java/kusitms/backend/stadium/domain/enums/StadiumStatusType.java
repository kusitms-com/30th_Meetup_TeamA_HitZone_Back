package kusitms.backend.stadium.domain.enums;

import kusitms.backend.result.domain.value.ReferencesGroup;

import java.util.List;

public interface StadiumStatusType {
    String getImgUrl();
    String getZoneName();
    String getZoneColor();
    String getOneLineDescription();
    List<String> getExplanations();
    String getFirstBaseSide();
    String getThirdBaseSide();
    String getTip();
    List<ReferencesGroup> getReferencesGroup();
    List<String> getPage1Keywords();
    List<String> getPage2Keywords();
    List<String> getPage3Keywords();
    List<String> getForbiddenKeywords();
    String[] getEntrance();
    String[] getStepSpacing();
    String[] getSeatSpacing();
    String[] getUsageInformation();
}
