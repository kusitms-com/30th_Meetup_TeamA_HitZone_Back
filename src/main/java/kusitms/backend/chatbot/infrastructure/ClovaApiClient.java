package kusitms.backend.chatbot.infrastructure;

import kusitms.backend.chatbot.dto.request.ClovaRequest;
import kusitms.backend.chatbot.dto.response.ClovaChatbotAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ClovaApiClient {
    private final WebClient webClient;

    // 외부 CLOVA API와 통신하는 메서드
    public String requestClova(ClovaRequest request) {
        ClovaChatbotAnswer clovaChatbotAnswer =  webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ClovaChatbotAnswer.class)
                .block();

        return clovaChatbotAnswer.result().message().content();
    }
}