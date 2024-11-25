package kusitms.backend.chatbot.dto.request;

import java.util.ArrayList;
import java.util.List;

public record ClovaRequestDto(
        ArrayList<MessageDto> messages,
        double topP,
        double temperature,
        int maxTokens,
        double repeatPenalty
) implements ChatbotRequestDto {

    @Override
    public List<MessageDto> getMessages() {
        return messages;
    }
}
