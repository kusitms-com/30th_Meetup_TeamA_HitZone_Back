package kusitms.backend.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDto(
        @NotBlank(message = "닉네임은 공백이나 빈칸일 수 없습니다.") String nickname
) {
}
