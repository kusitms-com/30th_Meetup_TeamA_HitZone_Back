package kusitms.backend.stadium.common;

import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class RecommendedTopRankedZones {

    public static <T extends Enum<T> & StadiumStatusType> List<Map<String, Object>> getTopRankedZones(
            T[] zones, List<String> clientKeywords) {

        List<Map<String, Object>> filteredZones = Arrays.stream(zones)
                .filter(zone -> !KeywordManager.hasForbiddenKeywords(zone.getForbiddenKeywords(), clientKeywords))
                .map(zone -> {

                    int page1Count = KeywordManager.getMatchingKeywordCount(zone.getPage1Keywords(), clientKeywords);
                    int page2Count = KeywordManager.getMatchingKeywordCount(zone.getPage2Keywords(), clientKeywords);
                    int page3Count = KeywordManager.getMatchingKeywordCount(zone.getPage3Keywords(), clientKeywords);

                    // 총 겹치는 개수 계산
                    int totalMatchCount = page1Count + page2Count + page3Count;

                    Map<String, Object> result = new HashMap<>();
                    result.put("zone", zone.getZone());
                    result.put("explanations", zone.getExplanations());
                    result.put("tip", zone.getTip());
                    result.put("referencesGroup", zone.getReferencesGroup());
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
                .limit(3)
                .map(result -> {
                    log.info("zone: {}, totalMatchCount: {}, page1Count: {}, page2Count: {}, page3Count: {}", result.get("zone"), result.get("totalMatchCount"), result.get("page1Count"), result.get("page2Count"), result.get("page3Count"));
                    result.remove("totalMatchCount");
                    result.remove("page1Count");
                    result.remove("page2Count");
                    result.remove("page3Count");
                    return result;
                })
                .toList();

        // 필터링된 결과가 없을 경우 첫 번째 구역을 반환
        if (filteredZones.isEmpty()) {
            return Arrays.stream(zones)
                    .findFirst()  // 첫 번째 구역을 선택
                    .map(zone -> {
                        // 필요한 필드들만 포함한 Map 생성
                        Map<String, Object> result = new HashMap<>();
                        result.put("zone", zone.getZone());
                        result.put("explanations", zone.getExplanations());
                        result.put("tip", zone.getTip());
                        result.put("referencesGroup", zone.getReferencesGroup());
                        log.info("zone: {}", result.get("zone"));
                        return Collections.singletonList(result);
                    })
                    .orElse(Collections.emptyList());
        }

        return filteredZones;
    }
}
