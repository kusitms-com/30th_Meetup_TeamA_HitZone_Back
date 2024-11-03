package kusitms.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckNicknameRequestDto (
        @NotBlank(message = "닉네임은 공백 또는 null값일 수 없습니다.") String nickname
) {
}
