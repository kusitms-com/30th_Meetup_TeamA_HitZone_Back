package kusitms.backend.auth.application;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.redis.RedisManager;
import kusitms.backend.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${spring.jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME; // 액세스 토큰 유효기간

    @Value("${spring.jwt.refresh-token.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME; // 리프레시 토큰 유효기간

    private final JWTUtil jwtUtil;
    private final RedisManager redisManager;

    @Transactional
    public void reIssueToken(String refreshToken, HttpServletResponse response) {
        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        String storedRefreshToken = redisManager.getRefreshToken(userId.toString());
        if (storedRefreshToken == null){
            throw new CustomException(AuthErrorStatus._EXPIRED_REFRESH_TOKEN);
        }
        if (!storedRefreshToken.equals(refreshToken)){
            throw new CustomException(AuthErrorStatus._TOKEN_USER_MISMATCH);
        }

        // 리프레시 토큰 검증
        jwtUtil.validateRefreshToken(storedRefreshToken);
        // 새로운 액세스 토큰과 리프레시 토큰 발급
        String newAccessToken = jwtUtil.generateToken(userId, ACCESS_TOKEN_EXPIRATION_TIME);  // 1시간 유효기간
        String newRefreshToken = jwtUtil.generateToken(userId, REFRESH_TOKEN_EXPIRATION_TIME);  // 14일 유효기간
        redisManager.saveRefreshToken(userId.toString(), newRefreshToken);

        CookieUtil.setCookie(response, "accessToken", newAccessToken, (int) (ACCESS_TOKEN_EXPIRATION_TIME * 1.5) / 1000);  // 1시간
        CookieUtil.setCookie(response, "refreshToken", newRefreshToken, (int) REFRESH_TOKEN_EXPIRATION_TIME / 1000);  // 14일
    }


}
