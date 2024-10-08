package kusitms.backend.user.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.user.application.UserService;
import kusitms.backend.user.dto.request.SignUpReq;
import kusitms.backend.user.dto.request.SendAuthCodeReq;
import kusitms.backend.user.dto.request.VerifyAuthCodeReq;
import kusitms.backend.user.dto.response.UserInfoRes;
import kusitms.backend.user.status.UserSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signupUser(@CookieValue String registerToken,
                                                        @Valid @RequestBody SignUpReq request){
        userService.signupUser(registerToken, request);
        return ApiResponse.onSuccess(UserSuccessStatus._CREATED_USER);
    }

    @GetMapping("/user-info")
    public ResponseEntity<ApiResponse<UserInfoRes>> getUserInfo() {
        return ApiResponse.onSuccess(UserSuccessStatus._OK_GET_USER_INFO, userService.getUserInfo());
    }

    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Void>> sendAuthCode(@Valid @RequestBody SendAuthCodeReq request) {
        userService.sendAuthCode(request);
        return ApiResponse.onSuccess(UserSuccessStatus._OK_SEND_AUTH_CODE);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Void>> verifyAuthCode(@Valid @RequestBody VerifyAuthCodeReq request) {
        userService.verifyAuthCode(request);
        return ApiResponse.onSuccess(UserSuccessStatus._OK_VERIFY_AUTH_CODE);
    }
}
