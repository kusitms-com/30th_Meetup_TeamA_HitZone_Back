package kusitms.backend.global.redis;

import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisManager {

    private final StringRedisTemplate redisTemplate;
    private final JWTUtil jwtUtil;

    @Value("${spring.redis.auth-code-expiration-minutes}")
    private int authCodeExpirationMinutes; // 인증 코드 유효기간 (분)

    @Value("${spring.redis.refresh-token-expiration-days}")
    private int refreshTokenExpirationDays; // 리프레시 토큰 유효기간 (일)

    /**
     * 휴대폰 인증 코드 저장.
     *
     * @param phoneNumber 휴대폰 번호
     * @param authCode    인증 코드
     */
    public void savePhoneVerificationCode(String phoneNumber, String authCode) {
        try {
            redisTemplate.opsForValue().set(phoneNumber, authCode, authCodeExpirationMinutes, TimeUnit.MINUTES);
            log.info("Saved auth code for phone number: {}", phoneNumber);
        } catch (Exception e) {
            log.error("Failed to save auth code for phone number: {}", phoneNumber, e);
            throw new CustomException(ErrorStatus._FAILED_SAVE_REDIS);
        }
    }

    /**
     * 휴대폰 인증 코드 조회.
     *
     * @param phoneNumber 휴대폰 번호
     * @return 인증 코드
     */
    public String getPhoneVerificationCode(String phoneNumber) {
        String authCode = redisTemplate.opsForValue().get(phoneNumber);
        if (authCode == null) {
            log.warn("Auth code not found for phone number: {}", phoneNumber);
            throw new CustomException(ErrorStatus._DATA_NOT_FOUND);
        }
        return authCode;
    }

    /**
     * 리프레시 토큰 저장.
     *
     * @param userId       사용자 ID
     * @param refreshToken 리프레시 토큰
     */
    public void saveRefreshToken(String userId, String refreshToken) {
        try {
            redisTemplate.opsForValue().set(userId, refreshToken, refreshTokenExpirationDays, TimeUnit.DAYS);
            log.info("Saved refresh token for userId: {}", userId);
        } catch (Exception e) {
            log.error("Failed to save refresh token for userId: {}", userId, e);
            throw new CustomException(ErrorStatus._FAILED_SAVE_REDIS);
        }
    }

    /**
     * 리프레시 토큰 검증 및 사용자 ID 추출.
     *
     * @param refreshToken 클라이언트로부터 전달받은 리프레시 토큰
     * @return 사용자 ID
     */
    public Long validateAndExtractUserId(String refreshToken) {
        Long userId = jwtUtil.validateAndExtractUserId(refreshToken);
        String storedRefreshToken = getStoredRefreshToken(userId.toString());
        if (!refreshToken.equals(storedRefreshToken)) {
            log.warn("Token mismatch for userId: {}", userId);
            throw new CustomException(AuthErrorStatus._TOKEN_USER_MISMATCH);
        }
        return userId;
    }

    /**
     * Redis에서 저장된 리프레시 토큰 조회.
     *
     * @param userId 사용자 ID
     * @return 저장된 리프레시 토큰
     */
    private String getStoredRefreshToken(String userId) {
        String token = redisTemplate.opsForValue().get(userId);
        if (token == null) {
            log.warn("Refresh token not found for userId: {}", userId);
            throw new CustomException(ErrorStatus._REDIS_TOKEN_NOT_FOUND);
        }
        return token;
    }
}
