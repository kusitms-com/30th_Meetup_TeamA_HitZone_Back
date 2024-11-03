package kusitms.backend.chatbot.status;

import kusitms.backend.global.code.BaseErrorCode;
import kusitms.backend.global.dto.ErrorReasonDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatbotErrorStatus implements BaseErrorCode {

    _NOT_FOUND_GUIDE_CHATBOT_ANSWER(HttpStatus.NOT_FOUND, "CHATBOT-001", "챗봇 답변을 찾을 수 없습니다."),
    _IS_NOT_VALID_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "CHATBOT-002", "올바른 카테고리명이 아닙니다.")
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