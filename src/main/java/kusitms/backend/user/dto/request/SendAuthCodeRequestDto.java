package kusitms.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SendAuthCodeRequestDto(
        @NotBlank(message = "휴대폰 번호는 공백이나 빈칸일 수 없습니다.") String phoneNumber
) {
}