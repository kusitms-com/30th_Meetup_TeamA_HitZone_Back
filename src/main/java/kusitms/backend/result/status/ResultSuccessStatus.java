package kusitms.backend.result.status;

import kusitms.backend.global.code.BaseCode;
import kusitms.backend.global.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultSuccessStatus implements BaseCode {
    _OK_SAVE_RECOMMEND_ZONES(HttpStatus.CREATED, "201", "추천 받은 유저성향과 구역을 저장하였습니다."),
    _OK_GET_RECOMMEND_PROFILE(HttpStatus.OK, "200", "해당 결과의 프로필 정보를 조회하였습니다."),
    _OK_GET_RECOMMEND_ZONES(HttpStatus.OK, "200", "해당 결과의 추천 구역 정보 리스트를 조회하였습니다.");

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
