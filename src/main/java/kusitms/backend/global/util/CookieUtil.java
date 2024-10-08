package kusitms.backend.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;

public class CookieUtil {

    // 쿠키 설정 메서드
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // 프론트가 https로 배포시 주석제거
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키에서 액세스 토큰 추출
    public static String getAccessTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new CustomException(AuthErrorStatus._MISSING_TOKEN);
    }

}
