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
 * 이 아래 휴대폰 인증과 관련된 부분은 로그인 방법이 여러가지가 있기에
 * 1인 1계정 처리를 위하여 휴대폰 인증을 추가하였습니다.
 * 하지만, 실제 좌석 예매 및 결제 부분이 존재하지 않아 1인 1계정 처리를 하지 않아도 될 것이라고
 * 판단하고 과금청구 위험도 윘기에 주석처리해두었습니다.
 */

    /**
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
