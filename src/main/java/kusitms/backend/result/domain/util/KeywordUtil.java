package kusitms.backend.result.domain.util;

import java.util.List;

public class KeywordUtil {

    public static int getMatchingKeywordCount(List<String> keywords, List<String> clientKeywords){
        return (int) keywords.stream()
                .filter(clientKeywords::contains)
                .count();
    }

    public static boolean hasForbiddenKeywords(List<String> forbiddenKeywords, List<String> clientKeywords){
        return forbiddenKeywords.stream()
                .anyMatch(clientKeywords::contains);
    }
}
