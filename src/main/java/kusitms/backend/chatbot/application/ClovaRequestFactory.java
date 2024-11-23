package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.dto.request.ChatbotRequest;
import kusitms.backend.chatbot.dto.request.ClovaRequest;
import kusitms.backend.chatbot.dto.request.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClovaRequestFactory {

    private final MessageFactory messageFactory;

    public ChatbotRequest createClovaRequest() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(messageFactory.createSystemMessage());

        return new ClovaRequest(messages, 0.8, 0.3, 256, 5.0);
    }
}
