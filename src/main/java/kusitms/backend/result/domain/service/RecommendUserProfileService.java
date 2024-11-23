package kusitms.backend.result.domain.service;

import kusitms.backend.result.domain.enums.ProfileStatusType;
import kusitms.backend.result.domain.util.KeywordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RecommendUserProfileService {

    public ProfileStatusType getRecommendedUserProfile(
            ProfileStatusType[] profiles,
            List<String> clientKeywords
    ) {

        ProfileStatusType filteredProfile = Arrays.stream(profiles)
                .filter(profile -> !KeywordUtil.hasForbiddenKeywords(profile.getForbiddenKeywords(), clientKeywords))
                .map(profile -> {
                    int page1Count = KeywordUtil.getMatchingKeywordCount(profile.getPage1Keywords(), clientKeywords);
                    int page2Count = KeywordUtil.getMatchingKeywordCount(profile.getPage2Keywords(), clientKeywords);
                    int page3Count = KeywordUtil.getMatchingKeywordCount(profile.getPage3Keywords(), clientKeywords);
                    int totalMatchCount = page1Count + page2Count + page3Count;

                    Map<String, Object> result = new HashMap<>();
                    result.put("profile", profile);
                    result.put("totalMatchCount", totalMatchCount);
                    result.put("page1Count", page1Count);
                    result.put("page2Count", page2Count);
                    result.put("page3Count", page3Count);
                    return result;
                })
                .filter(result -> (int) result.get("totalMatchCount") > 0)
                .sorted((a, b) -> {
                    int totalCompare = Integer.compare((int) b.get("totalMatchCount"), (int) a.get("totalMatchCount"));
                    if (totalCompare == 0) {
                        int page2Compare = Integer.compare((int) b.get("page2Count"), (int) a.get("page2Count"));
                        if (page2Compare == 0) {
                            int page3Compare = Integer.compare((int) b.get("page3Count"), (int) a.get("page3Count"));
                            if (page3Compare == 0) {
                                return Integer.compare((int) b.get("page1Count"), (int) a.get("page1Count"));
                            }
                            return page3Compare;
                        }
                        return page2Compare;
                    }
                    return totalCompare;
                })
                .findFirst()  // 첫 번째 결과를 가져옴
                .map(result -> {
                    log.info("zone: {}, totalMatchCount: {}, page1Count: {}, page2Count: {}, page3Count: {}",
                            result.get("profile"), result.get("totalMatchCount"),
                            result.get("page1Count"), result.get("page2Count"), result.get("page3Count"));
                    return (ProfileStatusType) result.get("profile");
                })
                .orElse(null);

        if (filteredProfile == null) {
            return Arrays.stream(profiles)
                    .findFirst()
                    .orElse(null);
        }
        return filteredProfile;
    }
}
