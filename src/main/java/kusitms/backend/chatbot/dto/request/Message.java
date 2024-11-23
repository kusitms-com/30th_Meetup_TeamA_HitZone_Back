package kusitms.backend.chatbot.dto.request;

public record Message(
        String role,
        String content
) {
}
