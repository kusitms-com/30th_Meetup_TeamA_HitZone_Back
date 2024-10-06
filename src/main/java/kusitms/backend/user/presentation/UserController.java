package kusitms.backend.user.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.user.application.UserService;
import kusitms.backend.user.dto.request.OnboardingReq;
import kusitms.backend.user.dto.response.UserInfoRes;
import kusitms.backend.user.status.UserSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signupUser(@CookieValue String registerToken,
                                                        @Valid @RequestBody OnboardingReq request){
        userService.signupUser(registerToken, request);
        return ApiResponse.onSuccess(UserSuccessStatus._CREATED_USER);
    }

    @GetMapping("/user-info")
    public ResponseEntity<ApiResponse<UserInfoRes>> getUserInfo() {
        return ApiResponse.onSuccess(UserSuccessStatus._OK_GET_USER_INFO, userService.getUserInfo());
    }
}
