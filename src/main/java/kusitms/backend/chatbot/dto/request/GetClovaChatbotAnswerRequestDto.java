package kusitms.backend.chatbot.dto.request;


import jakarta.validation.constraints.NotBlank;

public record GetClovaChatbotAnswerRequestDto(
        @NotBlank(message = "사용자 메세지는 빈 값일 수 없습니다.") String message
) {
}
