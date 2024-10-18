package kusitms.backend.global.status;

import kusitms.backend.global.code.BaseErrorCode;
import kusitms.backend.global.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // Global Error
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"500", "서버에서 요청을 처리 하는 동안 오류가 발생했습니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"400", "입력 값이 잘못된 요청 입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"401", "인증이 필요 합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "403", "금지된 요청 입니다."),
    _METHOD_NOT_ALLOWED(HttpStatus.FORBIDDEN, "403", "금지된 요청 입니다."),
    _FAILED_SAVE_REDIS(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-001", "Redis 저장에 실패하였습니다."),
    _FAILED_SERIALIZING_JSON(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-002", "JSON으로의 직렬화에 실패했습니다."),
    _FAILED_DESERIALIZING_JSON(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-003", "JSON에서 역직렬화에 실패했습니다.")
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