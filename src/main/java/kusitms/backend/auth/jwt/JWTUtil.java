package kusitms.backend.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    //  토큰 생성 메소드
    public String generateToken(Long userId, long expirationMillis) {
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 레지스터 토큰 생성 메소드 (여러 정보 포함)
    public String generateRegisterToken(String provider, String providerId, String email, long expirationMillis) {
        return Jwts.builder()
                .claim("provider", provider)
                .claim("providerId", providerId)
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 파싱 메소드
    private Claims tokenParser(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT : {}", token);
            throw new CustomException(AuthErrorStatus._EXPIRED_TOKEN);
        } catch (Exception e) {
            log.error("Invalid JWT : {}", token);
            throw new CustomException(AuthErrorStatus._INVALID_TOKEN);
        }
    }

    // 리프레쉬 토큰 파싱 메소드
    private Claims refreshTokenParser(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("Expired Refresh Token : {}", token);
            throw new CustomException(AuthErrorStatus._EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            log.error("Invalid Refresh Token : {}", token);
            throw new CustomException(AuthErrorStatus._INVALID_TOKEN);
        }
    }

    // JWT에서 userId 추출
    public Long getUserIdFromToken(String token) {
        return tokenParser(token).get("userId", Long.class);
    }

    // Register Token에서 provider 추출
    public String getProviderFromRegisterToken(String token) {
        return tokenParser(token).get("provider", String.class);
    }

    // Register Token에서 providerId 추출
    public String getProviderIdFromRegisterToken(String token) {
        return tokenParser(token).get("providerId", String.class);
    }

    // Register Token에서 email 추출
    public String getEmailFromRegisterToken(String token) {
        return tokenParser(token).get("email", String.class);
    }

    // 일반 토큰 유효성 검사
    public void validateToken(String token) {
        tokenParser(token);
    }

    // 리프레쉬 토큰 유효성 검사
    public void validateRefreshToken(String token) {
        refreshTokenParser(token);
    }
}
