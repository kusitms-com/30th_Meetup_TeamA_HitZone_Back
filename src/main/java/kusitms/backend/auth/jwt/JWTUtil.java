package kusitms.backend.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //  토큰 생성 메소드
    public String generateToken(Long userId, long expirationMillis) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    // 레지스터 토큰 생성 메소드 (여러 정보 포함) - 신규 유저 온보딩에 넘겨주기 위해서만 사용
    public String generateRegisterToken(String provider, String providerId, String email, long expirationMillis) {
        return Jwts.builder()
                .claim("provider", provider)
                .claim("providerId", providerId)
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    // 토큰 파싱 메소드
    public Claims tokenParser(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT : {}", token);
            throw new CustomException(AuthErrorStatus._EXPIRED_TOKEN);
        } catch (Exception e) {
            log.error("Invalid JWT : {}", token);
            throw new CustomException(AuthErrorStatus._INVALID_TOKEN);
        }
    }

    // 토큰 파싱 메소드
    public Claims refreshTokenParser(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.error("Expired Refresh Token : {}", token);
            throw new CustomException(AuthErrorStatus._EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.error("Invalid Refresh Token : {}", token);
            throw new CustomException(AuthErrorStatus._INVALID_TOKEN);
        }
    }

    public Long getUserIdFromToken(String token) {
        return tokenParser(token).get("userId", Long.class);
    }

    public String getProviderFromRegisterToken(String token) {
        return tokenParser(token).get("provider", String.class);
    }

    public String getProviderIdFromRegisterToken(String token) {
        return tokenParser(token).get("providerId", String.class);
    }

    public String getEmailFromRegisterToken(String token) {
        return tokenParser(token).get("email", String.class);
    }

    // 일반 토큰 유효성 검사 (리프레쉬 제외)
    public void validateToken(String token) {
        tokenParser(token);
    }

    // 리프레쉬 토큰 유효성 검사
    public void validateRefreshToken(String token) {
        refreshTokenParser(token);
    }

}
