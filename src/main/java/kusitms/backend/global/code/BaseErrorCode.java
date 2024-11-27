package kusitms.backend.global.code;

import kusitms.backend.global.dto.ErrorReasonDto;

public interface BaseErrorCode {
    ErrorReasonDto getReason();
    ErrorReasonDto getReasonHttpStatus();
}