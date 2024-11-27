package kusitms.backend.chatbot.infra.adapter;

import kusitms.backend.chatbot.application.dto.request.ChatbotRequestDto;
import kusitms.backend.chatbot.application.dto.request.ClovaRequestDto;
import kusitms.backend.chatbot.application.dto.response.ClovaChatbotAnswerDto;
import kusitms.backend.chatbot.domain.service.ChatbotApiClient;
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

    /**
     * 클로바 챗봇 API에 요청을 보내고 응답을 처리합니다.
     *
     * @param request 챗봇 요청 데이터
     * @return 클로바 챗봇 응답 메시지 내용 (문자열)
     * @throws CustomException 유효하지 않은 요청이거나 API 통신 에러 발생 시
     */
    @Override
    public Mono<String> requestChatbot(ChatbotRequestDto request) {
        if (!(request instanceof ClovaRequestDto clovaRequest)) {
            return Mono.error(new CustomException(ChatbotErrorStatus._INVALID_CHATBOT_REQUEST));
        }

        return webClient.post()
                .bodyValue(clovaRequest)
                .retrieve()
                .bodyToMono(ClovaChatbotAnswerDto.class)
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
