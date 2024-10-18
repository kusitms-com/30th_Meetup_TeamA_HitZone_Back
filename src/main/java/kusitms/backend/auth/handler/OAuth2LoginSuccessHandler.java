package kusitms.backend.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.dto.response.GoogleUserInfo;
import kusitms.backend.auth.dto.response.KakaoUserInfo;
import kusitms.backend.auth.dto.response.NaverUserInfo;
import kusitms.backend.auth.dto.response.OAuth2UserInfo;
import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.global.redis.RedisManager;
import kusitms.backend.global.util.CookieUtil;
import kusitms.backend.user.domain.entity.User;
import kusitms.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.jwt.redirect.onboarding}")
    private String REDIRECT_URI_ONBOARDING; // 신규 유저 리다이렉트할 URI

    @Value("${spring.jwt.redirect.base}")
    private String REDIRECT_URI_BASE; // 기존 유저 리다이렉트할 URI

    @Value("${spring.jwt.register-token.expiration-time}")
    private long REGISTER_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${spring.jwt.refresh-token.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private OAuth2UserInfo oAuth2UserInfo = null;
    private final JWTUtil jwtUtil;
    private final RedisManager redisManager;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        final String provider = token.getAuthorizedClientRegistrationId();
        final Map<String, Object> attributes = token.getPrincipal().getAttributes();
        switch (provider) {
            case "kakao" -> oAuth2UserInfo = new KakaoUserInfo(attributes);
            case "google" -> oAuth2UserInfo = new GoogleUserInfo(attributes);
            case "naver" -> oAuth2UserInfo = new NaverUserInfo(attributes);
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();

        User existUser = userRepository.findByProviderId(providerId);
        if (existUser == null) {
            log.info("신규 유저입니다.");
            String registerToken = jwtUtil.generateRegisterToken(provider, providerId, email, REGISTER_TOKEN_EXPIRATION_TIME);
            CookieUtil.setCookie(response, "registerToken", registerToken, (int) REGISTER_TOKEN_EXPIRATION_TIME / 1000);
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_ONBOARDING);
        } else {
            log.info("기존 유저입니다.");
            String accessToken = jwtUtil.generateToken(existUser.getId(), ACCESS_TOKEN_EXPIRATION_TIME);
            String refreshToken = jwtUtil.generateToken(existUser.getId(), REFRESH_TOKEN_EXPIRATION_TIME);

            redisManager.saveRefreshToken(existUser.getId().toString(), refreshToken);
            CookieUtil.setCookie(response, "accessToken", accessToken, (int) (ACCESS_TOKEN_EXPIRATION_TIME * 1.5) / 1000);
            CookieUtil.setCookie(response, "refreshToken", refreshToken, (int) REFRESH_TOKEN_EXPIRATION_TIME / 1000);
            CookieUtil.setNotHttpOnlyCookie(response, "expirationTime", String.valueOf((int) ACCESS_TOKEN_EXPIRATION_TIME / 1000), (int) (ACCESS_TOKEN_EXPIRATION_TIME * 1.5) / 1000);
            getRedirectStrategy().sendRedirect(request, response, REDIRECT_URI_BASE);
        }
    }
}
