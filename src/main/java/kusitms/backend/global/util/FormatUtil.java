package kusitms.backend.global.util;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.status.ErrorStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {

    /**
     * 전화번호 형식을 010-XXXX-XXXX 형태로 변경한다.
     * @param phoneNumber 휴대폰 번호
     * @return 변경된 형식의 전화번호
     */
    public static String formatPhoneNumber(String phoneNumber) {
        String pattern = "^010(\\d{4})(\\d{4})$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(phoneNumber.replaceAll("-", "")); // 입력값에서 "-" 제거
        if (matcher.matches()) {
            return "010-" + matcher.group(1) + "-" + matcher.group(2);
        } else {
            throw new CustomException(ErrorStatus._FAILED_FORMAT_PHONE_NUMBER);
        }
    }
}
