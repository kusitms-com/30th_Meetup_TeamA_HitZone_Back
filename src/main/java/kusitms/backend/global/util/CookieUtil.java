package kusitms.backend.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.status.ErrorStatus;

public class CookieUtil {

    /**
     * 인증 쿠키를 설정합니다 (Access Token, Refresh Token, Expiration Time).
     *
     * @param response          HTTP 응답 객체
     * @param accessToken       액세스 토큰
     * @param refreshToken      리프레시 토큰
     * @param accessTokenExpiry 액세스 토큰 만료 시간 (밀리초)
     * @param refreshTokenExpiry 리프레시 토큰 만료 시간 (밀리초)
     */
    public static void setAuthCookies(HttpServletResponse response, String accessToken, String refreshToken,
                                      long accessTokenExpiry, long refreshTokenExpiry) {
        setCookie(response, "accessToken", accessToken, (int) (accessTokenExpiry * 1.5) / 1000);
        setCookie(response, "refreshToken", refreshToken, (int) refreshTokenExpiry / 1000);
        setNotHttpOnlyCookie(response, "expirationTime",
                String.valueOf((int) accessTokenExpiry / 1000),
                (int) (accessTokenExpiry * 1.5) / 1000);
    }

    /**
     * HTTP-Only 쿠키를 설정합니다.
     *
     * @param response HTTP 응답 객체
     * @param name     쿠키 이름
     * @param value    쿠키 값
     * @param maxAge   쿠키 만료 시간 (초)
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * HTTP-Only가 아닌 쿠키를 설정합니다.
     *
     * @param response HTTP 응답 객체
     * @param name     쿠키 이름
     * @param value    쿠키 값
     * @param maxAge   쿠키 만료 시간 (초)
     */
    public static void setNotHttpOnlyCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 쿠키에서 Access Token을 추출합니다.
     *
     * @param request HTTP 요청 객체
     * @return 액세스 토큰
     * @throws CustomException 쿠키가 없거나 Access Token이 없는 경우
     */
    public static String getAccessTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new CustomException(ErrorStatus._MISSING_TOKEN_IN_COOKIE);
    }
}
