package kusitms.backend.stadium.common;

import kusitms.backend.stadium.domain.enums.ProfileStatusType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RecommendedUserProfile {

    public static Map<String, Object> getRecommendedUserProfile(
            ProfileStatusType[] profiles, List<String> clientKeywords) {

        return Arrays.stream(profiles)
                .filter(profile -> !KeywordManager.hasForbiddenKeywords(profile.getForbiddenKeywords(), clientKeywords))
                .map(profile -> {
                    int page1Count = KeywordManager.getMatchingKeywordCount(profile.getPage1Keywords(), clientKeywords);
                    int page2Count = KeywordManager.getMatchingKeywordCount(profile.getPage2Keywords(), clientKeywords);
                    int page3Count = KeywordManager.getMatchingKeywordCount(profile.getPage3Keywords(), clientKeywords);

                    // 총 겹치는 개수 계산
                    int totalMatchCount = page1Count + page2Count + page3Count;

                    Map<String, Object> result = new HashMap<>();
                    result.put("nickname", profile.getNickName());
                    result.put("type", profile.getType());
                    result.put("explanation", profile.getExplanation());
                    result.put("hashTags", profile.getHastTags());
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
                            result.get("nickname"), result.get("totalMatchCount"),
                            result.get("page1Count"), result.get("page2Count"), result.get("page3Count"));
                    result.remove("totalMatchCount");
                    result.remove("page1Count");
                    result.remove("page2Count");
                    result.remove("page3Count");
                    return result;
                })
                .orElse(new HashMap<>());
    }
}
