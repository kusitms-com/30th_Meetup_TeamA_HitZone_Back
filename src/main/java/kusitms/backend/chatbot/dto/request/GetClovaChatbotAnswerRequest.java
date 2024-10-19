package kusitms.backend.chatbot.dto.request;


import jakarta.validation.constraints.NotNull;

public record GetClovaChatbotAnswerRequest(
        @NotNull(message = "사용자 메세지는 필수값입니다.") String message
) {
}