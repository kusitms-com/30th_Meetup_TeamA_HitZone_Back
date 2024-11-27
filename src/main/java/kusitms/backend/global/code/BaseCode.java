package kusitms.backend.global.code;

import kusitms.backend.global.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();
    ReasonDto getReasonHttpStatus();
}