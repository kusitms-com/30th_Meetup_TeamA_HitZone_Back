package kusitms.backend.chatbot.dto.request;

import kusitms.backend.chatbot.domain.enums.Role;

public record Message(
        Role role,
        String content
) {
}