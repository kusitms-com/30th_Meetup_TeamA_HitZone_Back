package kusitms.backend.stadium.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TopRankedZoneRequestDto(
        @NotBlank(message = "스티디움명은 공백 또는 빈 값일 수 없습니다.") String stadium,
        @NotBlank(message = "선호 구역은 공백 또는 빈 값일 수 없습니다. 또한, 1루석 혹은 3루석으로 입력해주세요.") String preference,
        @NotEmpty(message = "키워드 배열은 빈 값이어선 안됩니다.") @Size(min = 1, message = "키워드 배열은 최소 한 개 이상의 값이 있어야 합니다.") String[] clientKeywords
) {
}
