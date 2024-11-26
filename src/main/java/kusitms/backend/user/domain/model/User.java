package kusitms.backend.user.domain.model;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.user.domain.enums.ProviderStatusType;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private Long id;
    private ProviderStatusType provider;
    private String providerId;
    private String email;
    private String nickname;
//    private String phoneNumber;

    public User(
            Long id,
            ProviderStatusType provider,
            String providerId,
            String email,
            String nickname
    ) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickname = nickname;
    }

    public static User toDomain(
            Long id,
            ProviderStatusType provider,
            String providerId,
            String email,
            String nickname
    ) {
        return new User(id, provider, providerId, email, nickname);
    }

    /**
     * 전화번호 형식을 010-XXXX-XXXX 형태로 변경한다.
     * @param phoneNumber 휴대폰 번호
     * @return 변경된 형식의 전화번호
     */
    private static String formatPhoneNumber(String phoneNumber) {
        String pattern = "^010(\\d{4})(\\d{4})$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(phoneNumber.replaceAll("-", "")); // 입력값에서 "-" 제거
        if (matcher.matches()) {
            return "010-" + matcher.group(1) + "-" + matcher.group(2);
        } else {
            throw new CustomException(UserErrorStatus._BAD_REQUEST_PHONE_NUMBER_TYPE);
        }
    }
}
