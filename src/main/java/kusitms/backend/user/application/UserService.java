package kusitms.backend.user.application;

import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.auth.jwt.SecurityContextProvider;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.redis.RedisManager;
import kusitms.backend.user.domain.entity.User;
import kusitms.backend.user.domain.repository.UserRepository;
import kusitms.backend.user.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.dto.request.SendAuthCodeRequestDto;
import kusitms.backend.user.dto.request.SignUpRequestDto;
import kusitms.backend.user.dto.request.VerifyAuthCodeRequestDto;
import kusitms.backend.user.dto.response.UserInfoResponseDto;
import kusitms.backend.user.status.UserErrorStatus;
import kusitms.backend.user.status.UserSuccessStatus;
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
    private final SmsService smsService;
    private final RedisManager redisManager;

    @Transactional
    public void signupUser(String registerToken, SignUpRequestDto request) {
        if (registerToken == null){
            throw new CustomException(AuthErrorStatus._EXPIRED_REGISTER_TOKEN);
        }
        jwtUtil.validateToken(registerToken);
        String provider = jwtUtil.getProviderFromRegisterToken(registerToken);
        String providerId = jwtUtil.getProviderIdFromRegisterToken(registerToken);
        String email = jwtUtil.getEmailFromRegisterToken(registerToken);

        // 1인 1계정 처리를 위한 이미 등록된 회원인지 확인하여 알려주기
//        User existedUser = userRepository.findByPhoneNumber(request.phoneNumber());
//        if (existedUser != null && existedUser.getProvider() != ProviderStatusType.of(provider)) {
//            if (existedUser.getProvider() == ProviderStatusType.of("kakao")) {
//                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_KAKAO);
//            } else if (existedUser.getProvider() == ProviderStatusType.of("google")) {
//                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_GOOGLE);
//            } else if (existedUser.getProvider() == ProviderStatusType.of("naver")) {
//                throw new CustomException(UserErrorStatus._EXISTING_USER_ACCOUNT_NAVER);
//            }
//        }

        userRepository.save(
                SignUpRequestDto.toEntity(
                        provider,
                        providerId,
                        email,
                        request.nickName()
                )
        );
        log.info("유저 회원가입 성공");
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo() {
        Long userId = SecurityContextProvider.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorStatus._NOT_FOUND_USER));
        log.info("유저 정보 조회 성공");
        return UserInfoResponseDto.from(user);
    }

    @Transactional
    public void sendAuthCode(SendAuthCodeRequestDto request) {
        String authCode = generateAuthCode();
        redisManager.saveAuthCode(request.phoneNumber(), authCode);
        smsService.sendSms(request.phoneNumber(),"히트존 인증 코드 번호 : " + authCode);
    }

    private String generateAuthCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // 6자리 인증 코드 생성
    }

    @Transactional(readOnly = true)
    public void verifyAuthCode(VerifyAuthCodeRequestDto request) {
        String savedCode = redisManager.getAuthCode(request.phoneNumber());
        if (savedCode == null) {
            throw new CustomException(UserErrorStatus._EXPIRED_AUTH_CODE);
        }
        // 인증코드 틀릴 경우
        if (!request.authCode().equals(savedCode)){
            throw new CustomException(UserErrorStatus._MISS_MATCH_AUTH_CODE);
        }
    }

    @Transactional(readOnly = true)
    public void checkNickname(CheckNicknameRequestDto request) {
        User user = userRepository.findByNickname(request.nickname());
        if (user != null) {
            throw new CustomException(UserErrorStatus._DUPLICATED_NICKNAME);
        }
    }
}
