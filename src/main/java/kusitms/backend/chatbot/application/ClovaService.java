package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.dto.request.ChatbotRequestDto;
import kusitms.backend.chatbot.dto.response.GetClovaChatbotAnswerResponseDto;
import kusitms.backend.chatbot.infrastructure.ChatbotApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClovaService {
    private final ChatbotApiClient chatbotApiClient;
    private final ClovaRequestFactory clovaRequestFactory;
    private final MessageFactory messageFactory;

    public Mono<GetClovaChatbotAnswerResponseDto> getClovaChatbotAnswer(String message) {
        ChatbotRequestDto request = clovaRequestFactory.createClovaRequest();
        request.getMessages().add(messageFactory.createUserMessage(message));

        return chatbotApiClient.requestChatbot(request)
                .map(GetClovaChatbotAnswerResponseDto::of);
    }
}
