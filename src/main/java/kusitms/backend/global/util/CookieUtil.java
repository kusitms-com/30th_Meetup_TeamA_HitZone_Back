package kusitms.backend.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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


}
