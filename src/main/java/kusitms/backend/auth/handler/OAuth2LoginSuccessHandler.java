package kusitms.backend.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.jwt.redirect.onboarding}")
    private String REDIRECT_URI_ONBOARDING;

    @Value("${spring.jwt.redirect.base}")
    private String REDIRECT_URI_BASE;

    private final UserService userService;

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
        if (userService.isNewUser(token)) {
            userService.handleNewUser(token, response);
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_ONBOARDING);
        }
        // 기존 사용자 처리
        else {
            userService.handleExistingUser(token, response);
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_BASE);
        }
    }
}
