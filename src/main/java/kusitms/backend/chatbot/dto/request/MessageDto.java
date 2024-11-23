package kusitms.backend.chatbot.dto.request;

public record MessageDto(
        String role,
        String content
) {
}
