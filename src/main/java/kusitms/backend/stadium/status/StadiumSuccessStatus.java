package kusitms.backend.stadium.status;

import kusitms.backend.global.code.BaseCode;
import kusitms.backend.global.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StadiumSuccessStatus implements BaseCode {
    _OK_GET_ZONES_NAME(HttpStatus.OK, "200", "해당 스타디움의 구역 이름들이 조회되었습니다."),
    _OK_GET_ZONE_GUIDE(HttpStatus.OK, "200", "해당 구역에 대한 가이드 정보가 조회되었습니다.");

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