package kusitms.backend.chatbot.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kusitms.backend.chatbot.application.ChatbotService;
import kusitms.backend.chatbot.application.ClovaService;
import kusitms.backend.chatbot.dto.request.GetClovaChatbotAnswerRequest;
import kusitms.backend.chatbot.dto.response.GetClovaChatbotAnswerResponse;
import kusitms.backend.chatbot.dto.response.GetGuideChatbotAnswerResponse;
import kusitms.backend.chatbot.status.ChatbotSuccessStatus;
import kusitms.backend.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
@Validated
public class ChatbotController {
    private final ChatbotService chatbotService;
    private final ClovaService clovaService;

    /**
     * 가이드 챗봇 답변 조회 API
     *
     * 이 API는 특정 경기장과 카테고리에 맞는 가이드 답변을 조회합니다.
     * 예를 들어, 사용자가 "stadium", "baseball", "manner" 등의 카테고리에 대해
     * 안내를 요청할 때, 해당 카테고리와 경기장에 맞는 정보를 제공합니다.
     *
     * @param stadiumName 유효하지 않은 값일 수 없는 경기장 이름
     * @param categoryName 유효하지 않은 값일 수 없는 카테고리 이름 (예: stadium, baseball 등)
     * @param orderNumber 1 이상의 유효한 순서 번호
     * @return 요청한 경기장과 카테고리에 맞는 가이드 답변 및 관련 이미지 URL
     */
    @GetMapping("/guide")
    public ResponseEntity<ApiResponse<GetGuideChatbotAnswerResponse>> getGuideChatbotAnswer(
            @RequestParam("stadiumName") @NotBlank String stadiumName,
            @RequestParam("categoryName") @NotBlank String categoryName,
            @RequestParam("orderNumber") @Min(1) int orderNumber){

        GetGuideChatbotAnswerResponse response = chatbotService.getGuideChatbotAnswer(stadiumName, categoryName, orderNumber);

        return ApiResponse.onSuccess(ChatbotSuccessStatus._GET_GUIDE_CHATBOT_ANSWER, response);
    }

    /**
     * Clova 챗봇 답변 조회 API
     *
     * 이 API는 Clova 챗봇을 사용하여 사용자의 요청 메시지에 대한 답변을 생성합니다.
     * 사용자가 입력한 메시지를 처리하고, Clova 챗봇 서비스로부터 답변을 받아와 반환합니다.
     *
     * @param request 사용자의 메시지를 포함하는 요청 객체 (유효성 검사가 적용됨)
     * @return Clova 챗봇으로부터 생성된 답변
     */
    @PostMapping("/clova")
    public Mono<ResponseEntity<ApiResponse<GetClovaChatbotAnswerResponse>>> getClovaChatbotAnswer(
            @Valid @RequestBody GetClovaChatbotAnswerRequest request) {

        return clovaService.getClovaChatbotAnswer(request.message())
                .map(response -> ApiResponse.onSuccess(ChatbotSuccessStatus._GET_CLOVA_CHATBOT_ANSWER, response));
    }
}
