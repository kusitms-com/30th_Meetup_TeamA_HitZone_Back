package kusitms.backend.user.infra.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.global.util.CookieUtil;
import kusitms.backend.user.application.UserApplicationService;
import kusitms.backend.user.application.dto.response.AuthTokenResponseDto;
import kusitms.backend.user.application.dto.response.RegisterTokenResponseDto;
import kusitms.backend.user.infra.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final UserApplicationService userApplicationService;

    /**
     * OAuth2 로그인 성공 후 처리.
     *
     * @param request        HTTP 요청 객체
     * @param response       HTTP 응답 객체
     * @param authentication 인증 객체
     * @throws IOException 리다이렉트 오류 발생 시 예외
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        // 신규 사용자 처리
        if (userApplicationService.isNewUser(token)) {
            RegisterTokenResponseDto registerTokenResponseDto = userApplicationService.handleNewUser(token, response);
            CookieUtil.setRegisterCookies(response, registerTokenResponseDto.registerToken(), registerTokenResponseDto.registerTokenExpiration());
            getRedirectStrategy().sendRedirect(request, response, jwtUtil.getRedirectOnboardingUrl());
        }
        // 기존 사용자 처리
        else {
            AuthTokenResponseDto authTokenResponseDto = userApplicationService.handleExistingUser(token, response);
            CookieUtil.setAuthCookies(response, authTokenResponseDto.accessToken(), authTokenResponseDto.refreshToken(),
                    authTokenResponseDto.accessTokenExpiration(), authTokenResponseDto.refreshTokenExpiration());
            getRedirectStrategy().sendRedirect(request, response, jwtUtil.getRedirectBaseUrl());
        }
    }
}
