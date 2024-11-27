package kusitms.backend.chatbot.application.dto.request;

public record MessageDto(
        String role,
        String content
) {
}
