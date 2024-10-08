package kusitms.backend.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.application.AuthService;
import kusitms.backend.auth.status.AuthSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
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

    @PutMapping("/token/re-issue")
    public ResponseEntity<ApiResponse<Void>> reIssueToken(@CookieValue(required = false) String refreshToken,
                                                          HttpServletResponse response) {
        authService.reIssueToken(refreshToken, response);
        return ApiResponse.onSuccess(AuthSuccessStatus._OK_RE_ISSUE_TOKEN);
    }
}
