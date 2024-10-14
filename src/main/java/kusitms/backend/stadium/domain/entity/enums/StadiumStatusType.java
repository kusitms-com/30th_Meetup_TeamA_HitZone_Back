package kusitms.backend.stadium.domain.entity.enums;

import kusitms.backend.stadium.common.ReferencesGroup;

import java.util.List;

public interface StadiumStatusType {
    String getZone();
    List<String> getExplanations();
    String getTip();
    List<ReferencesGroup> getReferencesGroup();
    List<String> getPage1Keywords();
    List<String> getPage2Keywords();
    List<String> getPage3Keywords();
    List<String> getForbiddenKeywords();
}
