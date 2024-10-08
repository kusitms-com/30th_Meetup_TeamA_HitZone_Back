package kusitms.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpReq {
        @NotBlank(message = "이름은 공백이나 빈칸일 수 없습니다.")
        private String name;
        @NotBlank(message = "휴대폰 번호는 공백이나 빈칸일 수 없습니다.")
        private String phoneNumber;
}
