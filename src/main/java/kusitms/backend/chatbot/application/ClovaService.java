package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.dto.request.ChatbotRequest;
import kusitms.backend.chatbot.dto.response.GetClovaChatbotAnswerResponse;
import kusitms.backend.chatbot.infrastructure.ChatbotApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClovaService {
    private final ChatbotApiClient chatbotApiClient;
    private final ClovaRequestFactory clovaRequestFactory;
    private final MessageFactory messageFactory;

    public GetClovaChatbotAnswerResponse getClovaChatbotAnswer(String message) {
        ChatbotRequest request = clovaRequestFactory.createClovaRequest();
        request.getMessages().add(messageFactory.createUserMessage(message));
        String answer = chatbotApiClient.requestChatbot(request);

        return GetClovaChatbotAnswerResponse.of(answer);
    }
}
