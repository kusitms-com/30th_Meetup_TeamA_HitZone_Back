package kusitms.backend.result.domain.service;

import kusitms.backend.result.domain.enums.ProfileStatusType;
import kusitms.backend.result.domain.util.KeywordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RecommendUserProfileManager {

    /**
     * 해당하는 키워드에 따른 프로필 정보를 반환한다.
     * @param profiles 5가지의 프로필 리스트 정보
     * @param clientKeywords 클라이언트로부터 받은 키워드 리스트
     * @return 추천 프로필 정보
     */
    public ProfileStatusType getRecommendedUserProfile(
            ProfileStatusType[] profiles,
            List<String> clientKeywords
    ) {
        List<ProfileStatusType> filteredProfiles = filterProfilesByForbiddenKeywords(profiles, clientKeywords);
        List<Map<String, Object>> profileDetails = mapProfilesByMatchDetails(filteredProfiles, clientKeywords);
        ProfileStatusType recommendedProfile = sortAndFindTopProfile(profileDetails);

        if (recommendedProfile == null) {
            return getDefaultProfile(profiles);
        }

        log.info("filteredProfile is returned: {}", recommendedProfile);
        return recommendedProfile;
    }

    /**
     * 금지된 키워드를 기반으로 필터링된 프로필 리스트 반환
     * @param profiles 5가지의 프로필 정보
     * @param clientKeywords 클라이언트로부터 받은 키워드 리스트
     * @return 금지된 키워드를 기반으로 필터링된 프로필 리스트
     */
    private List<ProfileStatusType> filterProfilesByForbiddenKeywords(
            ProfileStatusType[] profiles, List<String> clientKeywords
    ) {
        return Arrays.stream(profiles)
                .filter(profile -> !KeywordUtil.hasForbiddenKeywords(profile.getForbiddenKeywords(), clientKeywords))
                .toList();
    }

    /**
     * 일치하는 키워드가 1개 이상인 프로필 리스트를 반환한다.
     * @param profiles 금지된 키워드를 기반으로 필터링된 프로필 리스트
     * @param clientKeywords 클라이언트로부터 받은 키워드 리스트
     * @return 일치하는 키워드가 1개 이상인 프로필 리스트
     */
    private List<Map<String, Object>> mapProfilesByMatchDetails(
            List<ProfileStatusType> profiles, List<String> clientKeywords
    ) {
        return profiles.stream()
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

                    log.info("profile: {}, totalMatchCount: {}, page1Count: {}, page2Count: {}, page3Count: {}",
                            profile, totalMatchCount, page1Count, page2Count, page3Count);
                    return result;
                })
                .filter(result -> (int) result.get("totalMatchCount") > 0)
                .toList();
    }

    /**
     * 키워드 일치 정보를 기반으로 프로필을 정렬하고 첫 번째 프로필 반환
     * @param profileDetails 일치하는 프로필이 1개 이상인 프로필 리스트
     * @return 키워드에 가장 일치하는 프로필
     */
    private ProfileStatusType sortAndFindTopProfile(List<Map<String, Object>> profileDetails) {
        return profileDetails.stream()
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
                .findFirst()
                .map(result -> (ProfileStatusType) result.get("profile"))
                .orElse(null);
    }

    /**
     * 기본 프로필을 반환 (추천 프로필이 없는 경우)
     * @param profiles 5가지의 프로필 리스트
     * @return 리스트 중 1번째 프로필
     */
    private ProfileStatusType getDefaultProfile(ProfileStatusType[] profiles) {
        return Arrays.stream(profiles)
                .findFirst()
                .orElse(null);
    }
}