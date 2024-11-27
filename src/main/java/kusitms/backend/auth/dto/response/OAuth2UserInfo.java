package kusitms.backend.auth.dto.response;

public interface OAuth2UserInfo {
    //제공자 (Ex. naver, google, ...)
    String getProvider();
    //제공자에서 발급해주는 고유 아이디
    String getProviderId();
    //이메일
    String getEmail();
    //사용자 이름 (설정한 이름)
    String getName();
}
