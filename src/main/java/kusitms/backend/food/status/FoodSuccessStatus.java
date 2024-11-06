package kusitms.backend.food.status;

import kusitms.backend.global.code.BaseCode;
import kusitms.backend.global.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FoodSuccessStatus implements BaseCode {
    _OK_GET_INTERIOR_FOODS(HttpStatus.OK, "200", "구장내부 식사류 매장 조회가 완료되었습니다."),
    _OK_GET_EXTERIOR_FOODS(HttpStatus.OK, "200", "구장외부 식사류 매장 조회가 완료되었습니다."),
    _OK_GET_INTERIOR_DESSERTS(HttpStatus.OK, "200", "구장내부 디저트류 매장 조회가 완료되었습니다."),
    _OK_GET_EXTERIOR_DESSERTS(HttpStatus.OK, "200", "구장외부 디저트류 매장 조회가 완료되었습니다.");

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