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

    /**
     * Clova 챗봇 답변을 가져옵니다.
     *
     * @param message 사용자가 보낸 메시지
     * @return Clova 챗봇 답변을 포함하는 Mono 객체
     * @throws IllegalArgumentException 메시지가 null 또는 빈 문자열인 경우
     */
    public Mono<GetClovaChatbotAnswerResponseDto> getClovaChatbotAnswer(String message) {
        ChatbotRequestDto request = clovaRequestFactory.createClovaRequest();
        request.getMessages().add(messageFactory.createUserMessage(message));

        return chatbotApiClient.requestChatbot(request)
                .map(GetClovaChatbotAnswerResponseDto::of);
    }
}
