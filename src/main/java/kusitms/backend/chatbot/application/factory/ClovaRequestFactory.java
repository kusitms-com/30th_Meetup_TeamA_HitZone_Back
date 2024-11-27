package kusitms.backend.chatbot.application.factory;

import kusitms.backend.chatbot.application.dto.request.ChatbotRequestDto;
import kusitms.backend.chatbot.application.dto.request.ClovaRequestDto;
import kusitms.backend.chatbot.application.dto.request.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ClovaRequestFactory {

    private final MessageFactory messageFactory;

    /**
     * Clova 챗봇 요청 객체를 생성합니다.
     *
     * @return 생성된 Clova 요청 객체
     */
    public ChatbotRequestDto createClovaRequest() {
        ArrayList<MessageDto> messages = new ArrayList<>();
        messages.add(messageFactory.createSystemMessage());

        return new ClovaRequestDto(messages, 0.8, 0.3, 256, 5.0);
    }
}
