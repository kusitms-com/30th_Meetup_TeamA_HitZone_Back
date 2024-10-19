package kusitms.backend.chatbot.dto.request;

import java.util.ArrayList;

public record ClovaRequest(
        ArrayList<Message> messages,
        double topP,
        double temperature,
        int maxTokens,
        double repeatPenalty
) {
}