package kusitms.backend.chatbot.application.dto.request;

import java.util.List;

public interface ChatbotRequestDto {
    List<MessageDto> getMessages();
}
