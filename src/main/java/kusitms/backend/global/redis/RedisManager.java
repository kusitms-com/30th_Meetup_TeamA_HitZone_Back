package kusitms.backend.global.redis;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisManager {

    private final StringRedisTemplate redisTemplate;

    // 휴대폰 인증 코드 저장 (5분간 유효)
    public void saveAuthCode(String phoneNumber, String authCode) {
        try {
            redisTemplate.opsForValue().set(phoneNumber, authCode, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new CustomException(ErrorStatus._FAILED_SAVE_REDIS);
        }
    }

    // 휴대폰 인증 코드 조회
    public String getAuthCode(String phoneNumber) {
        return redisTemplate.opsForValue().get(phoneNumber);
    }

    // 리프레시 토큰 저장 (2주간 유효)
    public void saveRefreshToken(String userId, String refreshToken) {
        try {
            redisTemplate.opsForValue().set(userId, refreshToken, 14, TimeUnit.DAYS);
        } catch (Exception e) {
            throw new CustomException(ErrorStatus._FAILED_SAVE_REDIS);
        }
    }

    // 리프레시 토큰 조회
    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }
}