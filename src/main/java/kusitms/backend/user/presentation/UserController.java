package kusitms.backend.user.presentation;

import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.user.application.UserApplicationService;
import kusitms.backend.user.application.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.application.dto.request.SignUpRequestDto;
import kusitms.backend.user.application.dto.response.UserInfoResponseDto;
import kusitms.backend.user.status.UserSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserApplicationService userApplicationService;

    /**
     * 레지스터 토큰에서 추출한 정보들과 닉네임으로 회원가입을 진행한다.
     * @param registerToken 쿠키로부터 받은 레지스터 토큰
     * @param request 닉네임
     * @return void
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signupUser(
            @CookieValue(required = false) String registerToken,
            @Valid @RequestBody SignUpRequestDto request
    ) {
        userApplicationService.signupUser(registerToken, request);
        return ApiResponse.onSuccess(UserSuccessStatus._CREATED_USER);
    }

    /**
     * 쿠키를 통한 인가를 통해 유저 정보를 조회한다.
     * @param accessToken 쿠키로부터 받은 어세스 토큰
     * @return 유저 닉네임, 이메일
     */
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<UserInfoResponseDto>> getUserInfo(
            @CookieValue String accessToken
    ) {
        UserInfoResponseDto userInfoResponseDto = userApplicationService.getUserInfo(accessToken);
        return ApiResponse.onSuccess(UserSuccessStatus._OK_GET_USER_INFO, userInfoResponseDto);
    }

    /**
     * 닉네임이 중복유무를 확인한다.
     * @param request 유저 닉네임
     * @return void
     */
    @PostMapping("/nickname/check")
    public ResponseEntity<ApiResponse<Void>> checkNickname(
            @Valid @RequestBody CheckNicknameRequestDto request
    ) {
        userApplicationService.checkNickname(request);
        return ApiResponse.onSuccess(UserSuccessStatus._OK_NOT_DUPLICATED_NICKNAME);
    }

    /**
     * [2024.11.03 현재 미사용하는 기능]
     * [1인 1계정 처리 미사용과 과금부담으로 주석 처리]
     * 휴대폰에 6자리 인증코드를 보낸다.
     * @param request 휴대폰 번호
     * @return void
     */
//    @PostMapping("/code/send")
//    public ResponseEntity<ApiResponse<Void>> sendAuthCode(
//        @Valid @RequestBody SendAuthCodeRequestDto request
//    ) {
//        userService.sendAuthCode(request);
//        return ApiResponse.onSuccess(UserSuccessStatus._OK_SEND_AUTH_CODE);
//    }

    /**
     * 인증코드를 확인하여 휴대폰 인증을 진행한다.
     * @param request 클라이언트로부터 받은 휴대폰 번호와, 인증 코드
     * @return void
     */
//    @PostMapping("/code/verify")
//    public ResponseEntity<ApiResponse<Void>> verifyAuthCode(
//            @Valid @RequestBody VerifyAuthCodeRequestDto request
//    ) {
//        userService.verifyAuthCode(request);
//        return ApiResponse.onSuccess(UserSuccessStatus._OK_VERIFY_AUTH_CODE);
//    }
}
