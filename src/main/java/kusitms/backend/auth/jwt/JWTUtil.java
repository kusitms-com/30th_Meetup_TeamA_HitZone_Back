package kusitms.backend.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JWTUtil {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JWTUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWT 토큰 생성.
     *
     * @param claims          토큰에 포함할 클레임
     * @param expirationMillis 토큰 유효기간 (밀리초)
     * @return 생성된 토큰 문자열
     */
    public String generateToken(Map<String, Object> claims, long expirationMillis) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Access Token 또는 Refresh Token 생성.
     *
     * @param userId          사용자 ID
     * @param expirationMillis 토큰 유효기간 (밀리초)
     * @return 생성된 토큰 문자열
     */
    public String generateAccessOrRefreshToken(Long userId, long expirationMillis) {
        return generateToken(Map.of("userId", userId), expirationMillis);
    }

    /**
     * Register Token 생성.
     *
     * @param provider   OAuth2 제공자
     * @param providerId 제공자 ID
     * @param email      사용자 이메일
     * @return 생성된 Register Token
     */
    public String generateRegisterToken(String provider, String providerId, String email) {
        return generateToken(
                Map.of("provider", provider, "providerId", providerId, "email", email),
                jwtProperties.getRegisterTokenExpirationTime()
        );
    }

    /**
     * JWT 토큰 파싱 및 유효성 검사.
     *
     * @param token 토큰 문자열
     * @return 토큰의 클레임 정보
     */
    private Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("Expired Token: {}", token);
            throw new CustomException(AuthErrorStatus._EXPIRED_TOKEN);
        } catch (Exception e) {
            log.error("Invalid Token: {}", token);
            throw new CustomException(AuthErrorStatus._INVALID_TOKEN);
        }
    }

    /**
     * JWT 토큰 유효성 검사.
     *
     * @param token 토큰 문자열
     */
    public void validateToken(String token) {
        parseToken(token);
    }

    /**
     * JWT 토큰에서 사용자 ID 추출.
     *
     * @param token 토큰 문자열
     * @return 사용자 ID
     */
    public Long getUserIdFromToken(String token) {
        return parseToken(token).get("userId", Long.class);
    }

    /**
     * Register Token에서 OAuth2 제공자 추출.
     *
     * @param token Register Token 문자열
     * @return OAuth2 제공자
     */
    public String getProviderFromRegisterToken(String token) {
        return parseToken(token).get("provider", String.class);
    }

    /**
     * Register Token에서 제공자 ID 추출.
     *
     * @param token Register Token 문자열
     * @return 제공자 ID
     */
    public String getProviderIdFromRegisterToken(String token) {
        return parseToken(token).get("providerId", String.class);
    }

    /**
     * Register Token에서 이메일 추출.
     *
     * @param token Register Token 문자열
     * @return 사용자 이메일
     */
    public String getEmailFromRegisterToken(String token) {
        return parseToken(token).get("email", String.class);
    }

    /**
     * Refresh Token 검증 및 사용자 ID 추출.
     *
     * @param token Refresh Token 문자열
     * @return 사용자 ID
     */
    public Long validateAndExtractUserId(String token) {
        return getUserIdFromToken(token);
    }

    /**
     * Access Token 유효기간 반환.
     *
     * @return Access Token 유효기간 (밀리초)
     */
    public long getAccessTokenExpirationTime() {
        return jwtProperties.getAccessTokenExpirationTime();
    }

    /**
     * Refresh Token 유효기간 반환.
     *
     * @return Refresh Token 유효기간 (밀리초)
     */
    public long getRefreshTokenExpirationTime() {
        return jwtProperties.getRefreshTokenExpirationTime();
    }

    /**
     * Register Token 유효기간 반환.
     *
     * @return Register Token 유효기간 (밀리초)
     */
    public long getRegisterTokenExpirationTime() {
        return jwtProperties.getRegisterTokenExpirationTime();
    }

    /**
     * 온보딩 리다이렉트 URL 반환.
     *
     * @return 온보딩 리다이렉트 URL
     */
    public String getRedirectOnboardingUrl() {
        return jwtProperties.getRedirectOnboarding();
    }

    /**
     * 로그인 성공 후 리다이렉트 URL 반환.
     *
     * @return 로그인 성공 후 리다이렉트 URL
     */
    public String getRedirectBaseUrl() {
        return jwtProperties.getRedirectBase();
    }
}
