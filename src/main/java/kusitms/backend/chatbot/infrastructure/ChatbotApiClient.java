package kusitms.backend.chatbot.infrastructure;

import kusitms.backend.chatbot.dto.request.ChatbotRequest;

public interface ChatbotApiClient {
    /**
     * 외부 챗봇 API와 통신하여 답변을 가져옵니다.
     *
     * @param request 추상 챗봇 요청 객체
     * @return 챗봇의 응답 메시지
     */
    String requestChatbot(ChatbotRequest request);
}
