package kusitms.backend.user.infra.jwt;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.user.status.AuthErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@Slf4j
public class SecurityContextProvider {

    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal)) {
            throw new CustomException(AuthErrorStatus._NOT_AUTHENTICATED);
        }
        return (Long) principal;
    }

// 테스트를 위한 코드
    public static void setupSecurityContextForTest(Long userId) {
        UserAuthentication authentication = new UserAuthentication(userId, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
