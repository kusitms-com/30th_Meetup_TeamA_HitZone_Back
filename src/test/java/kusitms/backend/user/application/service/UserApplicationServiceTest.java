package kusitms.backend.user.application.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.redis.DistributedLockManager;
import kusitms.backend.global.redis.RedisManager;
import kusitms.backend.user.application.UserApplicationService;
import kusitms.backend.user.application.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.application.dto.request.SendAuthCodeRequestDto;
import kusitms.backend.user.application.dto.request.VerifyAuthCodeRequestDto;
import kusitms.backend.user.application.dto.response.AuthTokenResponseDto;
import kusitms.backend.user.domain.enums.ProviderStatusType;
import kusitms.backend.user.domain.model.User;
import kusitms.backend.user.domain.repository.UserRepository;
import kusitms.backend.user.infra.jwt.JWTUtil;
import kusitms.backend.user.infra.sms.SmsManager;
import kusitms.backend.user.status.AuthErrorStatus;
import kusitms.backend.user.status.UserErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserApplicationServiceTest {

    @InjectMocks
    private UserApplicationService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private SmsManager smsManager;

    @Mock
    private RedisManager redisManager;

    @Mock
    private DistributedLockManager distributedLockManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 닉네임 중복 확인 성공 테스트.
     */
    @Test
    void testCheckNickname_Success() {
        // Given
        String nickname = "newNickname";
        CheckNicknameRequestDto request = new CheckNicknameRequestDto(nickname);
        String lockKey = "lock:nickname:" + nickname;

        when(distributedLockManager.acquireLock(lockKey, 5000)).thenReturn(true);
        when(userRepository.findUserByNickname(nickname)).thenReturn(null);

        // When & Then
        assertDoesNotThrow(() -> service.checkNickname(request));
        verify(distributedLockManager, times(1)).releaseLock(lockKey);
    }

    /**
     * 닉네임 중복 확인 실패 테스트 (중복된 닉네임).
     */
    @Test
    void testCheckNickname_Failure() {
        // Given
        String nickname = "existingNickname";
        CheckNicknameRequestDto request = new CheckNicknameRequestDto(nickname);
        String lockKey = "lock:nickname:" + nickname;

        when(distributedLockManager.acquireLock(lockKey, 5000)).thenReturn(true);
        when(userRepository.findUserByNickname(nickname))
                .thenReturn(User.toDomain(1L, ProviderStatusType.KAKAO, "providerId", "email@example.com", nickname));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> service.checkNickname(request));
        assertEquals(UserErrorStatus._DUPLICATED_NICKNAME, exception.getErrorCode());
        verify(distributedLockManager, times(1)).releaseLock(lockKey);
    }

    /**
     * 인증 코드 발송 테스트.
     */
    @Test
    void testSendAuthCode_Success() {
        // Given
        String phoneNumber = "01012345678";
        SendAuthCodeRequestDto request = new SendAuthCodeRequestDto(phoneNumber);

        // When
        service.sendAuthCode(request);

        // Then
        verify(redisManager, times(1)).savePhoneVerificationCode(eq(phoneNumber), anyString());
        verify(smsManager, times(1)).sendSms(eq(phoneNumber), contains("히트존 인증 코드 번호"));
    }

    /**
     * 인증 코드 검증 성공 테스트.
     */
    @Test
    void testVerifyAuthCode_Success() {
        // Given
        String phoneNumber = "01012345678";
        String authCode = "123456";
        VerifyAuthCodeRequestDto request = new VerifyAuthCodeRequestDto(phoneNumber, authCode);

        when(redisManager.getPhoneVerificationCode(phoneNumber)).thenReturn(authCode);

        // When & Then
        assertDoesNotThrow(() -> service.verifyAuthCode(request));
    }

    /**
     * 인증 코드 검증 실패 테스트 (코드 불일치).
     */
    @Test
    void testVerifyAuthCode_Mismatch() {
        // Given
        String phoneNumber = "01012345678";
        String savedAuthCode = "123456";
        String wrongAuthCode = "654321";
        VerifyAuthCodeRequestDto request = new VerifyAuthCodeRequestDto(phoneNumber, wrongAuthCode);

        when(redisManager.getPhoneVerificationCode(phoneNumber)).thenReturn(savedAuthCode);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> service.verifyAuthCode(request));
        assertEquals(UserErrorStatus._MISS_MATCH_AUTH_CODE, exception.getErrorCode());
    }

    /**
     * Refresh Token 재발급 성공 테스트.
     */
    @Test
    void testReIssueToken_Success() {
        // Given
        String refreshToken = "validRefreshToken";
        Long userId = 1L;

        when(redisManager.validateAndExtractUserId(refreshToken)).thenReturn(userId);
        when(jwtUtil.generateAccessOrRefreshToken(userId, jwtUtil.getAccessTokenExpirationTime())).thenReturn("newAccessToken");
        when(jwtUtil.generateAccessOrRefreshToken(userId, jwtUtil.getRefreshTokenExpirationTime())).thenReturn("newRefreshToken");

        // When
        AuthTokenResponseDto result = service.reIssueToken(refreshToken);

        // Then
        assertNotNull(result);
        assertEquals("newRefreshToken", result.refreshToken());
    }

    /**
     * Refresh Token 재발급 실패 테스트 (토큰 만료).
     */
    @Test
    void testReIssueToken_Failure() {
        // Given
        String expiredRefreshToken = null;

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> service.reIssueToken(expiredRefreshToken));
        assertEquals(AuthErrorStatus._EXPIRED_REFRESH_TOKEN, exception.getErrorCode());
    }
}
