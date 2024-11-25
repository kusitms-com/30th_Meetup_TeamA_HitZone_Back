package kusitms.backend.result.domain.service;

import kusitms.backend.result.domain.util.KeywordUtil;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class RecommendTopRankedZonesManager {

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
        List<T> filteredZones = filteredZonesByForbiddenKeywords(zones, clientKeywords);
        List<Map<String, Object>> zoneDetails = mapZoneByMatchDetails(filteredZones, clientKeywords);
        List<T> topZones = sortAndLimitZones(zoneDetails);
        List<T> resultZones = addPriorityDummyZones(zones, topZones);
        log.info("filteredZones is returned: {}", resultZones);
        return resultZones;
    }

    /**
     * 구역 리스트 중 금지된 키워드와 겹치는 구역을 제외하고 반환한다.
     * @param zones 해당 구장의 구역 ENUM 리스트
     * @param clientKeywords 클라이언트로부터 받은 키워드 배열
     * @return 금지된 키워드 필터링 된 구역 리스트
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    private <T extends Enum<T> & StadiumStatusType> List<T> filteredZonesByForbiddenKeywords(
            T[] zones, List<String> clientKeywords
    ) {
        return Arrays.stream(zones)
                .filter(zone -> !KeywordUtil.hasForbiddenKeywords(zone.getForbiddenKeywords(), clientKeywords))
                .toList();
    }

    /**
     * 구역 리스트를 Map에 필요한 정보만 선별하여 저장하고 키워드가 최소 1개 겹치는 구역만 필터링한다.
     * @param zones 해당 구장의 구역 ENUM 리스트
     * @param clientKeywords 클라이언트로부터 받은 키워드 배열
     * @return 최소 1개 키워드 일치된 구역 리스트
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    private <T extends Enum<T> & StadiumStatusType> List<Map<String, Object>> mapZoneByMatchDetails(
            List<T> zones, List<String> clientKeywords
    ) {
        return zones.stream()
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

                    log.info("zone: {}, totalMatchCount: {}, page1Count: {}, page2Count: {}, page3Count: {}",
                            zone, totalMatchCount, page1Count, page2Count, page3Count);
                    return result;
                })
                .filter(result -> (int) result.get("totalMatchCount") > 0)
                .toList();
    }

    /**
     * 1,2,3순위에 일치하는 키워드 개수를 센후 1순위 > 2순위 > 3순위 개수가 많은 순데로 정렬한다.
     * @param zoneDetails 최소 1개 키워드 일치하는 구역 리스트
     * @return 1,2,3순위로 정렬된 구역 리스트
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    private <T extends Enum<T> & StadiumStatusType> List<T> sortAndLimitZones(
            List<Map<String, Object>> zoneDetails
    ) {
        return zoneDetails.stream()
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
                .map(result -> (T) result.get("zone"))
                .toList();
    }

    /**
     * 개수가 3개 미만일 시 우선순위가 높은 구역 리스트 중 상위 순서부터 추가하여 3개로 맞추어준다.
     * @param zones 키워드에 따른 최종 필터링 된 구역 리스트
     * @param topZones 개수가 3개 미만일 시 추가해주는 우산순위 높은 구역 리스트
     * @return 3개의 추천된 구역 리스트
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    private <T extends Enum<T> & StadiumStatusType> List<T> addPriorityDummyZones(
            T[] zones, List<T> topZones
    ) {
        List<T> priorityDummyZones = Arrays.stream(zones)
                .filter(zone -> {
                    int ordinal = zone.ordinal();
                    return ordinal == 0 || ordinal == 1 || ordinal == 2;
                })
                .toList();

        List<T> resultZones = new ArrayList<>(topZones); // Create modifiable list
        for (T priorityDummyZone : priorityDummyZones) {
            if (resultZones.size() >= 3) {
                break;
            }
            if (!resultZones.contains(priorityDummyZone)) {
                resultZones.add(priorityDummyZone);
            }
        }
        return resultZones;
    }
}
