package kusitms.backend.user.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.user.application.UserService;
import kusitms.backend.user.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.dto.request.SignUpRequestDto;
import kusitms.backend.user.dto.response.UserInfoResponseDto;
import kusitms.backend.user.status.UserSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    /**
     * 유저 회원가입을 진행한다.
     * @return X
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signupUser(
            @CookieValue(required = false) String registerToken,
            @Valid @RequestBody SignUpRequestDto request
    ) {
        userService.signupUser(registerToken, request);
        return ApiResponse.onSuccess(UserSuccessStatus._CREATED_USER);
    }

    /**
     * 유저 정보를 조회한다.
     * @return 닉네임, 이메일
     */
    @GetMapping("/user-info")
    public ResponseEntity<ApiResponse<UserInfoResponseDto>> getUserInfo() {
        return ApiResponse.onSuccess(UserSuccessStatus._OK_GET_USER_INFO, userService.getUserInfo());
    }

    /**
     * 회원가입시 닉네임 중복 확인을 한다.
     * @return x
     */
    @PostMapping("/nickname/check")
    public ResponseEntity<ApiResponse<Void>> checkNickname(
            @Valid @RequestBody CheckNicknameRequestDto request
    ) {
        userService.checkNickname(request);
        return ApiResponse.onSuccess(UserSuccessStatus._OK_NOT_DUPLICATED_NICKNAME);
    }


    /**
     * [2024.11.03 현재 미사용하는 기능]
     * [1인 1계정 처리 미사용과 과금부담으로 주석 처리]
     * 휴대폰에 6자리 인증코드를 보낸다.
     * @return X
     */
//    @PostMapping("/send-code")
//    public ResponseEntity<ApiResponse<Void>> sendAuthCode(
//        @Valid @RequestBody SendAuthCodeRequestDto request
//    ) {
//        userService.sendAuthCode(request);
//        return ApiResponse.onSuccess(UserSuccessStatus._OK_SEND_AUTH_CODE);
//    }
    /**
     * 인증코드를 확인하여 휴대폰 인증을 진행한다.
     * @return X
     */
//    @PostMapping("/verify-code")
//    public ResponseEntity<ApiResponse<Void>> verifyAuthCode(
//            @Valid @RequestBody VerifyAuthCodeRequestDto request
//    ) {
//        userService.verifyAuthCode(request);
//        return ApiResponse.onSuccess(UserSuccessStatus._OK_VERIFY_AUTH_CODE);
//    }
}
