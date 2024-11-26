package kusitms.backend.user.status;

import kusitms.backend.global.code.BaseErrorCode;
import kusitms.backend.global.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorStatus implements BaseErrorCode {
    _INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-001", "유효하지 않은 토큰입니다."),
    _NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "AUTH-002", "사용자 인증에 실패했습니다."),
    _EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-003", "만료된 토큰입니다."),
    _EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-004", "리프레쉬 토큰이 만료되었습니다. 재로그인을 시도해주세요."),
    _EXPIRED_REGISTER_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-005", "추가정보 입력시간이 만료되었습니다. 재 회원가입을 시도해주세요."),
    _NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "AUTH-006", "DB에 조건에 해당하는 토큰이 존재하지 않습니다."),
    _TOKEN_USER_MISMATCH(HttpStatus.FORBIDDEN, "AUTH-007", "인증된 사용자 정보와 토큰이 일치하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}
