package kusitms.backend.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import kusitms.backend.user.domain.model.User;
import kusitms.backend.user.domain.enums.ProviderStatusType;

public record SignUpRequestDto(
        @NotBlank(message = "닉네임은 공백이나 빈칸일 수 없습니다.") String nickname
) {
        public static User toEntity(String provider, String providerId, String email, String nickname) {
                return User.builder()
                        .provider(ProviderStatusType.of(provider))
                        .providerId(providerId)
                        .email(email)
                        .nickname(nickname)
                        .build();
        }

        // 전화번호를 010-xxxx-xxxx 형식으로 변환
//        private static String formatPhoneNumber(String phoneNumber) {
//                String pattern = "^010(\\d{4})(\\d{4})$";
//                Pattern regex = Pattern.compile(pattern);
//                // "-"를 제거한 후 정규식과 매칭 시도
//                Matcher matcher = regex.matcher(phoneNumber.replaceAll("-", "")); // 입력값에서 "-" 제거
//                // 정규식이 맞으면 010-xxxx-xxxx 형식으로 변환
//                if (matcher.matches()) {
//                        return "010-" + matcher.group(1) + "-" + matcher.group(2);  // 010-xxxx-xxxx 형식으로 변환
//                } else {
//                        // 정규식에 맞지 않으면 예외 발생
//                        throw new CustomException(UserErrorStatus._BAD_REQUEST_PHONE_NUMBER_TYPE);
//                }
//        }
}
