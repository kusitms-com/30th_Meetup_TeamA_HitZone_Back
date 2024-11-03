package kusitms.backend.user.status;

import kusitms.backend.global.code.BaseCode;
import kusitms.backend.global.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserSuccessStatus implements BaseCode {

    _CREATED_USER(HttpStatus.CREATED, "201", "회원가입이 완료되었습니다."),
    _OK_GET_USER_INFO(HttpStatus.OK, "200", "유저 정보 조회가 완료되었습니다."),
    _OK_SEND_AUTH_CODE(HttpStatus.OK, "200", "휴대폰 인증 코드가 전송되었습니다."),
    _OK_VERIFY_AUTH_CODE(HttpStatus.OK, "200", "휴대폰 인증에 성공하였습니다."),
    _OK_NOT_DUPLICATED_NICKNAME(HttpStatus.OK, "200", "사용할 수 있는 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .isSuccess(true)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .isSuccess(true)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}