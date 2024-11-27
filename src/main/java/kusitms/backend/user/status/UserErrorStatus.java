package kusitms.backend.user.status;

import kusitms.backend.global.code.BaseErrorCode;
import kusitms.backend.global.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorStatus implements BaseErrorCode {

    _NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER-001", "해당 사용자를 찾을 수 없습니다."),
    _BAD_REQUEST_PROVIDER_STATUS_TYPE(HttpStatus.BAD_REQUEST, "USER-002", "잘못된 provider값입니다. (local, kakao, google, naver 중 하나로 입력해주세요.)"),
    _BAD_REQUEST_PHONE_NUMBER_TYPE(HttpStatus.BAD_REQUEST, "USER-003", "잘못된 휴대폰 번호 형식입니다. 010-0000-0000 형식으로 입력해주세요."),
    _EXPIRED_AUTH_CODE(HttpStatus.UNAUTHORIZED, "USER-004", "인증코드가 만료되었습니다. 인증코드를 재발급해주세요."),
    _MISS_MATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "USER-005", "인증코드가 일치하지 않습니다."),
    _EXISTING_USER_ACCOUNT_KAKAO(HttpStatus.FORBIDDEN, "USER-006", "이미 회원가입된 카카오 계정이 존재합니다."),
    _EXISTING_USER_ACCOUNT_GOOGLE(HttpStatus.FORBIDDEN, "USER-007", "이미 회원가입된 구글 계정이 존재합니다."),
    _EXISTING_USER_ACCOUNT_NAVER(HttpStatus.FORBIDDEN, "USER-008", "이미 회원가입된 네이버 계정이 존재합니다."),
    _DUPLICATED_NICKNAME(HttpStatus.FORBIDDEN, "USER-009", "중복된 닉네임이 존재합니다. 다른 닉네임을 입력해주세요.");

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