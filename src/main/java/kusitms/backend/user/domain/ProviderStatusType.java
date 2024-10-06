package kusitms.backend.user.domain;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProviderStatusType {
    LOCAL("local"),
    KAKAO("kakao"),
    GOOGLE("google"),
    NAVER("naver");

    private final String providerName;

    public static ProviderStatusType of(String providerName) {
        for (ProviderStatusType status : ProviderStatusType.values()){
            if (status.getProviderName().equalsIgnoreCase(providerName)){
                return status;
            }
        }
        throw new CustomException(UserErrorStatus._BAD_REQUEST_PROVIDER_STATUS_TYPE);
    }
}
