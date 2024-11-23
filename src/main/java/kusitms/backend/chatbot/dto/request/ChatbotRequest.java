package kusitms.backend.chatbot.dto.request;

import java.util.List;

public interface ChatbotRequest {
    List<Message> getMessages();
}
