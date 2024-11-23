package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.dto.request.ChatbotRequestDto;
import kusitms.backend.chatbot.dto.request.ClovaRequestDto;
import kusitms.backend.chatbot.dto.request.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClovaRequestFactory {

    private final MessageFactory messageFactory;

    public ChatbotRequestDto createClovaRequest() {
        ArrayList<MessageDto> messages = new ArrayList<>();
        messages.add(messageFactory.createSystemMessage());

        return new ClovaRequestDto(messages, 0.8, 0.3, 256, 5.0);
    }
}
