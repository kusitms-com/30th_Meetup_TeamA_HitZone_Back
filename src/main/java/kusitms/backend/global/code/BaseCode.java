package kusitms.backend.global.code;

import kusitms.backend.global.dto.ReasonDto;

public interface BaseCode {
    public ReasonDto getReason();

    public ReasonDto getReasonHttpStatus();
}