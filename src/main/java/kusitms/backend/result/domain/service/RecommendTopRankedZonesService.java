package kusitms.backend.result.domain.service;

import kusitms.backend.result.domain.util.KeywordUtil;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecommendTopRankedZonesService {

    /**
     * 해당하는 키워드에 따른 추천 구역을 3가지 반환한다.
     * @param zones 해당 구장의 구역 리스트 정보
     * @param clientKeywords 클라이언트로부터 받은 키워드 리스트
     * @return 3개의 추천 구역정보 리스트
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    public <T extends Enum<T> & StadiumStatusType> List<T> getTopRankedZones(
            T[] zones,
            List<String> clientKeywords
    ) {

        List<T> filteredZones = Arrays.stream(zones)
                .filter(zone -> !KeywordUtil.hasForbiddenKeywords(zone.getForbiddenKeywords(), clientKeywords))
                .map(zone -> {
                    int page1Count = KeywordUtil.getMatchingKeywordCount(zone.getPage1Keywords(), clientKeywords);
                    int page2Count = KeywordUtil.getMatchingKeywordCount(zone.getPage2Keywords(), clientKeywords);
                    int page3Count = KeywordUtil.getMatchingKeywordCount(zone.getPage3Keywords(), clientKeywords);
                    int totalMatchCount = page1Count + page2Count + page3Count;

                    Map<String, Object> result = new HashMap<>();
                    result.put("zone", zone);
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
                    return (T) result.get("zone");
                })
                .collect(Collectors.toCollection(ArrayList::new)); // 수정 가능한 리스트 생성

        List<T> priorityDummyZones = Arrays.stream(zones)
                .filter(zone -> {
                    int ordinal = zone.ordinal();
                    return ordinal == 0 || ordinal == 1 || ordinal == 2;
                })
                .toList();

        for (T priorityDummyZone : priorityDummyZones) {
            if (filteredZones.size() >= 3) {
                break;
            }
            if (!filteredZones.contains(priorityDummyZone)) {
                filteredZones.add(priorityDummyZone);
            }
        }

        log.info("filteredZones is returned: {}", filteredZones);
        return filteredZones;
    }
}
