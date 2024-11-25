package kusitms.backend.stadium.status;

import kusitms.backend.global.code.BaseCode;
import kusitms.backend.global.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EntertainmentSuccessStatus implements BaseCode {

    _OK_GET_INTERIOR_ENTERTAINMENTS(HttpStatus.OK, "200", "구장내부 즐길거리 조회가 완료되었습니다."),
    _OK_GET_EXTERIOR_ENTERTAINMENTS(HttpStatus.OK, "200", "구장외부 즐길거리 조회가 완료되었습니다.");

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
