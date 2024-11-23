package kusitms.backend.chatbot.dto.request;

import java.util.ArrayList;
import java.util.List;

public record ClovaRequest(
        ArrayList<Message> messages,
        double topP,
        double temperature,
        int maxTokens,
        double repeatPenalty
) implements ChatbotRequest {

    @Override
    public List<Message> getMessages() {
        return messages;
    }
}
