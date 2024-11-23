package kusitms.backend.auth.application;

import kusitms.backend.auth.dto.response.TokenResponseDto;
import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.redis.RedisManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTUtil jwtUtil;
    private final RedisManager redisManager;

    /**
     * Refresh Token 검증 및 새로운 토큰 발급.
     *
     * @param refreshToken 클라이언트로부터 받은 리프레시 토큰
     * @return 새로 생성된 Access Token과 Refresh Token
     */
    public TokenResponseDto reIssueToken(String refreshToken) {
        if (refreshToken == null) {
            throw new CustomException(AuthErrorStatus._EXPIRED_REFRESH_TOKEN);
        }

        Long userId = redisManager.validateAndExtractUserId(refreshToken);
        String newAccessToken = jwtUtil.generateAccessOrRefreshToken(userId, jwtUtil.getAccessTokenExpirationTime());
        String newRefreshToken = jwtUtil.generateAccessOrRefreshToken(userId, jwtUtil.getRefreshTokenExpirationTime());

        redisManager.saveRefreshToken(userId.toString(), newRefreshToken);
        return new TokenResponseDto(newAccessToken, newRefreshToken, jwtUtil.getAccessTokenExpirationTime(), jwtUtil.getRefreshTokenExpirationTime());
    }
}
