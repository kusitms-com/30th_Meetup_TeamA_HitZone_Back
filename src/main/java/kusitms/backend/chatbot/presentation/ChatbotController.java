package kusitms.backend.chatbot.presentation;

import jakarta.websocket.server.PathParam;
import kusitms.backend.chatbot.application.ChatbotService;
import kusitms.backend.chatbot.dto.GetGuideChatbotAnswerResponse;
import kusitms.backend.chatbot.status.ChatbotSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    // 가이드 챗봇 답변 조회 API
    @GetMapping("/guide")
    public ResponseEntity<ApiResponse<GetGuideChatbotAnswerResponse>> getGuideChatbotAnswer(
            @PathParam("stadiumName") String stadiumName,
            @PathParam("categoryName") String categoryName,
            @PathParam("orderNumber") int orderNumber){

        GetGuideChatbotAnswerResponse response = chatbotService.getGuideChatbotAnswer(stadiumName, categoryName, orderNumber);

        return ApiResponse.onSuccess(ChatbotSuccessStatus._GET_GUIDE_CHATBOT_ANSWER, response);
    }
}
