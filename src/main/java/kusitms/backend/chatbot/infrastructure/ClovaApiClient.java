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
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClovaApiClient implements ChatbotApiClient {
    private final WebClient webClient;

    @Override
    public Mono<String> requestChatbot(ChatbotRequest request) {
        if (!(request instanceof ClovaRequest clovaRequest)) {
            return Mono.error(new CustomException(ChatbotErrorStatus._INVALID_CHATBOT_REQUEST));
        }

        return webClient.post()
                .bodyValue(clovaRequest)
                .retrieve()
                .bodyToMono(ClovaChatbotAnswer.class)
                .flatMap(clovaChatbotAnswer -> {
                    if (clovaChatbotAnswer == null || clovaChatbotAnswer.result() == null || clovaChatbotAnswer.result().message() == null) {
                        return Mono.error(new CustomException(ChatbotErrorStatus._NOT_FOUND_GUIDE_CHATBOT_ANSWER));
                    }
                    return Mono.just(clovaChatbotAnswer.result().message().content());
                })
                .onErrorMap(WebClientResponseException.class, e -> new CustomException(ChatbotErrorStatus._CHATBOT_API_COMMUNICATION_ERROR))
                .onErrorMap(Exception.class, e -> new CustomException(ChatbotErrorStatus._CHATBOT_API_COMMUNICATION_ERROR));
    }
}
