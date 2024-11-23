package kusitms.backend.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DistributedLockManager {
    private final StringRedisTemplate redisTemplate;

    /**
     * 분산락 획득 시도.
     *
     * @param lockKey   락 키
     * @param timeoutMs 락 만료 시간 (밀리초)
     * @return 락 획득 여부
     */
    public boolean acquireLock(String lockKey, long timeoutMs) {
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCKED", timeoutMs, TimeUnit.MILLISECONDS);
        return Boolean.TRUE.equals(isLocked);
    }

    /**
     * 분산락 해제.
     *
     * @param lockKey 락 키
     */
    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
