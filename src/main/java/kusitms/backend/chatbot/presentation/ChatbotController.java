package kusitms.backend.chatbot.presentation;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import kusitms.backend.chatbot.application.ChatbotService;
import kusitms.backend.chatbot.application.ClovaService;
import kusitms.backend.chatbot.dto.request.GetClovaChatbotAnswerRequest;
import kusitms.backend.chatbot.dto.response.GetClovaChatbotAnswerResponse;
import kusitms.backend.chatbot.dto.response.GetGuideChatbotAnswerResponse;
import kusitms.backend.chatbot.status.ChatbotSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;
    private final ClovaService clovaService;

    // 가이드 챗봇 답변 조회 API
    @GetMapping("/guide")
    public ResponseEntity<ApiResponse<GetGuideChatbotAnswerResponse>> getGuideChatbotAnswer(
            @PathParam("stadiumName") String stadiumName,
            @PathParam("categoryName") String categoryName,
            @PathParam("orderNumber") int orderNumber){

        GetGuideChatbotAnswerResponse response = chatbotService.getGuideChatbotAnswer(stadiumName, categoryName, orderNumber);

        return ApiResponse.onSuccess(ChatbotSuccessStatus._GET_GUIDE_CHATBOT_ANSWER, response);
    }

    // Clova 챗봇 답변 조회 API
    @PostMapping("/clova")
    public ResponseEntity<ApiResponse<GetClovaChatbotAnswerResponse>> getClovaChatbotAnswer(
            @Valid @RequestBody GetClovaChatbotAnswerRequest request) {

        GetClovaChatbotAnswerResponse response = clovaService.getClovaChatbotAnswer(request.message());

        return ApiResponse.onSuccess(ChatbotSuccessStatus._GET_CLOVA_CHATBOT_ANSWER, response);
    }
}
