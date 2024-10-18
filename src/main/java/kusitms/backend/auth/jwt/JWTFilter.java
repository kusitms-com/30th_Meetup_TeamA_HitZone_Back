package kusitms.backend.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws CustomException, ServletException, IOException {
        try {
            String accessToken = CookieUtil.getAccessTokenFromCookies(request);
            jwtUtil.validateToken(accessToken);
            Long userId = jwtUtil.getUserIdFromToken(accessToken);
            setAuthentication(request, userId);
        }
        catch (CustomException e) {
            handleCustomException(response, e);
            return;
        }
        catch (Exception e) {
            handleException(response, e);  // 기타 예외 처리
            return;
        }
        filterChain.doFilter(request, response);
    }

    // 필터를 적용하지 않을 경로 설정
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/") || path.startsWith("/login") || path.startsWith("/public")
                || path.equals("/api/v1/test-error") || path.equals("/api/v1/health-check")
                || path.equals("/onboarding") || path.equals("/base")
                || path.equals("/api/v1/signup") || path.equals("/api/v1/send-code") || path.equals("/api/v1/verify-code")
                || path.equals("/api/v1/token/re-issue")
                || path.equals("/api/v1/zones/recommend") || path.equals("/api/v1/profiles")
                || path.startsWith("/api/v1/chatbot");
    }

    // 사용자 인증 설정
    private void setAuthentication(HttpServletRequest request, Long userId) {
        UserAuthentication authentication = new UserAuthentication(userId, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 토큰 예외 처리 메서드
    private void handleCustomException(HttpServletResponse response, CustomException e) throws IOException {
        log.error("JWT 커스텀 예외 발생: {}", e.getMessage());
        response.setStatus(e.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = String.format("{\"code\": \"%s\", \"message\": \"%s\", \"isSuccess\": \"false\"}", e.getCode(), e.getMessage());
        response.getWriter().write(jsonResponse);
    }

    // 공통 예외 처리 메서드
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        log.error("서버 예외 발생: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = String.format("{\"code\": \"%s\", \"message\": \"%s\", \"isSuccess\": \"false\"}", "500", e.getMessage());
        response.getWriter().write(jsonResponse);
    }
}