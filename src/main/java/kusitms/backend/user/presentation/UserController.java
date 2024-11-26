package kusitms.backend.user.presentation;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.util.CookieUtil;
import kusitms.backend.user.application.UserApplicationService;
import kusitms.backend.user.application.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.application.dto.request.SignUpRequestDto;
import kusitms.backend.user.application.dto.response.TokenResponseDto;
import kusitms.backend.user.application.dto.response.UserInfoResponseDto;
import kusitms.backend.user.status.AuthSuccessStatus;
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
    public ResponseEntity<ApiResponse<TokenResponseDto>> signupUser(
            @CookieValue(required = false) String registerToken,
            @Valid @RequestBody SignUpRequestDto request,
            HttpServletResponse response
    ) {
        TokenResponseDto tokenResponseDto = userApplicationService.signupUser(registerToken, request);
        CookieUtil.setAuthCookies(response, tokenResponseDto.accessToken(), tokenResponseDto.refreshToken(),
                tokenResponseDto.accessTokenExpiration(), tokenResponseDto.refreshTokenExpiration());

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
     * 리프레시 토큰을 통해 새로운 액세스 토큰과 리프레시 토큰을 발급받아 쿠키에 저장합니다.
     *
     * @param refreshToken 클라이언트로부터 전달받은 리프레시 토큰 (쿠키에서 추출)
     * @param response     새로운 토큰을 클라이언트 쿠키에 저장하기 위한 HTTP 응답 객체
     * @return 성공 응답
     * @throws CustomException 리프레시 토큰이 없거나 유효하지 않을 경우 예외 발생
     */
    @PutMapping("/token/re-issue")
    public ResponseEntity<ApiResponse<Void>> reIssueToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {

        // AuthService를 통해 새 토큰 생성
        TokenResponseDto tokenResponseDto = userApplicationService.reIssueToken(refreshToken);

        // 새 토큰을 쿠키에 설정
        CookieUtil.setAuthCookies(response, tokenResponseDto.accessToken(), tokenResponseDto.refreshToken(),
                tokenResponseDto.accessTokenExpiration(), tokenResponseDto.refreshTokenExpiration());

        return ApiResponse.onSuccess(AuthSuccessStatus._OK_RE_ISSUE_TOKEN);
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
