package kusitms.backend.chatbot.infrastructure;

import kusitms.backend.chatbot.dto.request.ChatbotRequest;
import kusitms.backend.chatbot.dto.request.ClovaRequest;
import kusitms.backend.chatbot.dto.response.ClovaChatbotAnswer;
import kusitms.backend.chatbot.status.ChatbotErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
public class ClovaApiClient implements ChatbotApiClient {
    private final WebClient webClient;

    @Override
    public String requestChatbot(ChatbotRequest request) {
        if (!(request instanceof ClovaRequest clovaRequest)) {
            throw new CustomException(ChatbotErrorStatus._INVALID_CHATBOT_REQUEST);
        }

        try {
            ClovaChatbotAnswer clovaChatbotAnswer = webClient.post()
                    .bodyValue(clovaRequest)
                    .retrieve()
                    .bodyToMono(ClovaChatbotAnswer.class)
                    .block();

            if (clovaChatbotAnswer == null || clovaChatbotAnswer.result() == null || clovaChatbotAnswer.result().message() == null) {
                throw new CustomException(ChatbotErrorStatus._NOT_FOUND_GUIDE_CHATBOT_ANSWER);
            }

            return clovaChatbotAnswer.result().message().content();

        } catch (WebClientResponseException e) {
            // 외부 API 응답 처리 중 발생한 예외 처리
            throw new CustomException(ChatbotErrorStatus._CHATBOT_API_COMMUNICATION_ERROR);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new CustomException(ChatbotErrorStatus._CHATBOT_API_COMMUNICATION_ERROR);
        }
    }
}
