package kusitms.backend.user.application;

import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.auth.jwt.SecurityContextProvider;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.redis.RedisManager;
import kusitms.backend.user.domain.ProviderStatusType;
import kusitms.backend.user.domain.User;
import kusitms.backend.user.domain.repository.UserRepository;
import kusitms.backend.user.dto.request.OnboardingReq;
import kusitms.backend.user.dto.response.UserInfoRes;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RedisManager redisManager;

    @Transactional
    public void signupUser(String registerToken, OnboardingReq request) {
        jwtUtil.validateToken(registerToken);
        String provider = jwtUtil.getProviderFromRegisterToken(registerToken);
        String providerId = jwtUtil.getProviderIdFromRegisterToken(registerToken);
        String email = jwtUtil.getEmailFromRegisterToken(registerToken);

        User existedUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existedUser != null && existedUser.getProvider() != ProviderStatusType.of(provider)) {
            if (existedUser.getProvider() == ProviderStatusType.of("kakao")) {
                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_KAKAO);
            } else if (existedUser.getProvider() == ProviderStatusType.of("google")) {
                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_GOOGLE);
            } else if (existedUser.getProvider() == ProviderStatusType.of("naver")) {
                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_NAVER);
            }
        }

        userRepository.save(
                User.to(
                        provider,
                        providerId,
                        email,
                        request.getName(),
                        request.getPhoneNumber())
        );
        log.info("유저 회원가입 성공");
    }

    @Transactional(readOnly = true)
    public UserInfoRes getUserInfo() {
        Long userId = SecurityContextProvider.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorStatus._NOT_FOUND_USER));
        log.info("유저 정보 조회 성공");
        return UserInfoRes.of(user);
    }


}
