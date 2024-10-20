package kusitms.backend.stadium.status;

import kusitms.backend.global.code.BaseErrorCode;
import kusitms.backend.global.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StadiumErrorStatus implements BaseErrorCode {
    _NOT_FOUND_STADIUM(HttpStatus.NOT_FOUND, "STADIUM-001", "해당하는 스타디움이 존재하지 않습니다. 구장이름을 다시 한번 확인해주세요."),
    _NOT_FOUND_ZONE(HttpStatus.NOT_FOUND, "STADIUM-002", "해당 스타디움에 존재하지 않는 존이름입니다. 다시 한번 확인해주세요.");

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
