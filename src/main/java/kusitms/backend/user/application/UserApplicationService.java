package kusitms.backend.user.application;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.dto.response.GoogleUserInfo;
import kusitms.backend.auth.dto.response.KakaoUserInfo;
import kusitms.backend.auth.dto.response.NaverUserInfo;
import kusitms.backend.auth.dto.response.OAuth2UserInfo;
import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.redis.DistributedLockManager;
import kusitms.backend.global.redis.RedisManager;
import kusitms.backend.global.util.CookieUtil;
import kusitms.backend.user.domain.entity.User;
import kusitms.backend.user.domain.repository.UserRepository;
import kusitms.backend.user.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.dto.request.SendAuthCodeRequestDto;
import kusitms.backend.user.dto.request.SignUpRequestDto;
import kusitms.backend.user.dto.request.VerifyAuthCodeRequestDto;
import kusitms.backend.user.dto.response.UserInfoResponseDto;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApplicationService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final SmsService smsService;
    private final RedisManager redisManager;
    private final DistributedLockManager distributedLockManager;
    
    @Transactional(readOnly = true)
    public void validateUserExistsById(Long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorStatus._NOT_FOUND_USER));
        log.info("유저 정보 조회 성공");
    }



    private static final long LOCK_TIMEOUT = 5000; // 5초

    /**
     * OAuth2 사용자 정보 추출 및 신규 여부 확인.
     *
     * @param token OAuth2 인증 토큰
     * @return 신규 사용자 여부
     */
    public boolean isNewUser(OAuth2AuthenticationToken token) {
        OAuth2UserInfo oAuth2UserInfo = extractOAuth2UserInfo(token);
        return userRepository.findByProviderId(oAuth2UserInfo.getProviderId()) == null;
    }

    /**
     * 신규 사용자 처리.
     *
     * @param token    OAuth2 인증 토큰
     * @param response HTTP 응답 객체
     */
    public void handleNewUser(OAuth2AuthenticationToken token, HttpServletResponse response) {
        OAuth2UserInfo oAuth2UserInfo = extractOAuth2UserInfo(token);
        String registerToken = jwtUtil.generateRegisterToken(
                oAuth2UserInfo.getProvider(),
                oAuth2UserInfo.getProviderId(),
                oAuth2UserInfo.getEmail()
        );
        CookieUtil.setCookie(response, "registerToken", registerToken, (int) jwtUtil.getRegisterTokenExpirationTime() / 1000);
        log.info("신규 사용자 처리 완료: {}", oAuth2UserInfo.getEmail());
    }

    /**
     * 기존 사용자 처리.
     *
     * @param token    OAuth2 인증 토큰
     * @param response HTTP 응답 객체
     */
    public void handleExistingUser(OAuth2AuthenticationToken token, HttpServletResponse response) {
        OAuth2UserInfo oAuth2UserInfo = extractOAuth2UserInfo(token);
        User existUser = userRepository.findByProviderId(oAuth2UserInfo.getProviderId());

        String accessToken = jwtUtil.generateAccessOrRefreshToken(existUser.getId(), jwtUtil.getAccessTokenExpirationTime());
        String refreshToken = jwtUtil.generateAccessOrRefreshToken(existUser.getId(), jwtUtil.getRefreshTokenExpirationTime());

        redisManager.saveRefreshToken(existUser.getId().toString(), refreshToken);
        CookieUtil.setAuthCookies(response, accessToken, refreshToken, jwtUtil.getAccessTokenExpirationTime(), jwtUtil.getRefreshTokenExpirationTime());
        log.info("기존 사용자 처리 완료: {}", existUser.getId());
    }

    /**
     * OAuth2 사용자 정보 추출.
     *
     * @param token OAuth2 인증 토큰
     * @return 추출된 사용자 정보
     */
    private OAuth2UserInfo extractOAuth2UserInfo(OAuth2AuthenticationToken token) {
        String provider = token.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = token.getPrincipal().getAttributes();

        return switch (provider) {
            case "kakao" -> new KakaoUserInfo(attributes);
            case "google" -> new GoogleUserInfo(attributes);
            case "naver" -> new NaverUserInfo(attributes);
            default -> throw new IllegalArgumentException("지원하지 않는 제공자: " + provider);
        };
    }

    /**
     * 회원가입 처리.
     *
     * @param registerToken 회원가입 토큰
     * @param request       회원가입 요청 정보
     */

    @Transactional
    public void signupUser(String registerToken, SignUpRequestDto request) {
        if (registerToken == null) {
            throw new CustomException(AuthErrorStatus._EXPIRED_REGISTER_TOKEN);
        }

        jwtUtil.validateToken(registerToken);
        String provider = jwtUtil.getProviderFromRegisterToken(registerToken);
        String providerId = jwtUtil.getProviderIdFromRegisterToken(registerToken);
        String email = jwtUtil.getEmailFromRegisterToken(registerToken);

        /* 1인 1계정 처리를 위한 이미 등록된 회원인지 확인하여 알려주기 (현재 미사용하는 기능으로 주석 처리)
        User existedUser = userRepository.findByPhoneNumber(request.phoneNumber());
        if (existedUser != null && existedUser.getProvider() != ProviderStatusType.of(provider)) {
            if (existedUser.getProvider() == ProviderStatusType.of("kakao")) {
                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_KAKAO);
            } else if (existedUser.getProvider() == ProviderStatusType.of("google")) {
                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_GOOGLE);
            } else if (existedUser.getProvider() == ProviderStatusType.of("naver")) {
                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_NAVER);
            }
        }*/

        userRepository.save(
                SignUpRequestDto.toEntity(
                        provider,
                        providerId,
                        email,
                        request.nickname()
                )
        );
        log.info("회원가입 성공: {}", email);
    }

    /**
     * 사용자 정보 조회.
     *
     * @param accessToken 액세스 토큰
     * @return 사용자 정보
     */
    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(String accessToken) {
        Long userId = jwtUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorStatus._NOT_FOUND_USER));
        log.info("사용자 정보 조회 성공: {}", userId);
        return UserInfoResponseDto.from(user);
    }

    /**
     * 인증 코드 발송.
     *
     * @param request 인증 코드 요청 정보
     */
    public void sendAuthCode(SendAuthCodeRequestDto request) {
        String authCode = generateAuthCode();
        redisManager.savePhoneVerificationCode(request.phoneNumber(), authCode);
        smsService.sendSms(request.phoneNumber(), "히트존 인증 코드 번호 : " + authCode);
        log.info("인증 코드 발송 완료: {}", request.phoneNumber());
    }

    /**
     * 인증 코드 생성.
     *
     * @return 생성된 인증 코드
     */
    private String generateAuthCode() {
        final int AUTH_CODE_BOUND = 900000;
        final int AUTH_CODE_OFFSET = 100000;
        return String.valueOf((int) (Math.random() * AUTH_CODE_BOUND) + AUTH_CODE_OFFSET);
    }

    /**
     * 인증 코드 검증.
     *
     * @param request 인증 코드 검증 요청 정보
     */
    public void verifyAuthCode(VerifyAuthCodeRequestDto request) {
        String savedCode = redisManager.getPhoneVerificationCode(request.phoneNumber());
        if (savedCode == null) {
            throw new CustomException(UserErrorStatus._EXPIRED_AUTH_CODE);
        }
        if (!request.authCode().equals(savedCode)) {
            throw new CustomException(UserErrorStatus._MISS_MATCH_AUTH_CODE);
        }
        log.info("인증 코드 검증 성공: {}", request.phoneNumber());
    }

    /**
     * 닉네임 중복 확인.
     *
     * @param request 닉네임 중복 확인 요청 정보
     */
    @Transactional
    public void checkNickname(CheckNicknameRequestDto request) {
        String nickname = request.nickname();
        String lockKey = "lock:nickname:" + nickname;

        // 1. 락 획득 시도
        if (!distributedLockManager.acquireLock(lockKey, LOCK_TIMEOUT)) {
            throw new CustomException(UserErrorStatus._DUPLICATED_NICKNAME);
        }

        try {
            // 2. 닉네임 중복 확인
            User user = userRepository.findByNickname(nickname);
            if (user != null) {
                throw new CustomException(UserErrorStatus._DUPLICATED_NICKNAME);
            }

            log.info("닉네임 사용 가능: {}", nickname);
        } finally {
            // 3. 락 해제
            distributedLockManager.releaseLock(lockKey);
        }
    }
}
