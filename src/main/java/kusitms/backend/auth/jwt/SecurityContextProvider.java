package kusitms.backend.auth.jwt;

import kusitms.backend.auth.status.AuthErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@Slf4j
public class SecurityContextProvider {

    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || isAnonymousUser()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal)) {
            throw new CustomException(AuthErrorStatus._NOT_AUTHENTICATED);
        }
        return (Long) principal;
    }

    // anonymousUser 확인 메소드
    public static boolean isAnonymousUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ANONYMOUS"));
    }

// 테스트를 위한 코드
    public static void setupSecurityContextForTest(Long userId) {
        UserAuthentication authentication = new UserAuthentication(userId, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
