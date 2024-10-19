package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.dto.request.ClovaRequest;
import kusitms.backend.chatbot.dto.response.GetClovaChatbotAnswerResponse;
import kusitms.backend.chatbot.infrastructure.ClovaApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClovaService {
    private final ClovaApiClient clovaApiClient;
    private final ClovaRequestFactory clovaRequestFactory;
    private final MessageFactory messageFactory;

    // Clova 챗봇 답변을 가져오는 메서드
    public GetClovaChatbotAnswerResponse getClovaChatbotAnswer(String message) {
        ClovaRequest request = clovaRequestFactory.createClovaRequest();
        request.messages().add(messageFactory.createUserMessage(message));
        String answer = clovaApiClient.requestClova(request);

        return GetClovaChatbotAnswerResponse.of(answer);
    }
}