package kusitms.backend.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.application.AuthService;
import kusitms.backend.auth.dto.response.TokenResponse;
import kusitms.backend.auth.status.AuthSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    /**
     * 리프레시 토큰을 통해 새로운 액세스 토큰과 리프레시 토큰을 발급받아 쿠키에 저장합니다.
     *
     * @param refreshToken 클라이언트로부터 전달받은 리프레시 토큰 (쿠키에서 추출)
     * @param response     새로운 토큰을 클라이언트 쿠키에 저장하기 위한 HTTP 응답 객체
     * @return 성공 응답
     * @throws CustomException 리프레시 토큰이 없거나 유효하지 않을 경우 예외 발생
     */
    @PutMapping("/token/re-issue")
    public ResponseEntity<ApiResponse<Void>> reIssueToken(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {

        // AuthService를 통해 새 토큰 생성
        TokenResponse tokenResponse = authService.reIssueToken(refreshToken);

        // 새 토큰을 쿠키에 설정
        CookieUtil.setAuthCookies(response, tokenResponse.accessToken(), tokenResponse.refreshToken(),
                tokenResponse.accessTokenExpiration(), tokenResponse.refreshTokenExpiration());

        return ApiResponse.onSuccess(AuthSuccessStatus._OK_RE_ISSUE_TOKEN);
    }
}
