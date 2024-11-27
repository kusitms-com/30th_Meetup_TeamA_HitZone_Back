package kusitms.backend.auth.dto.response;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return ((Map<String, Object>) attribute.get("response")).get("id").toString();
    }

    @Override
    public String getEmail() {
        return ((Map<String, Object>) attribute.get("response")).get("email").toString();
    }

    @Override
    public String getName() {
        return ((Map<String, Object>) attribute.get("response")).get("name").toString();
    }
}
