package kusitms.backend.chatbot.dto.response;

import kusitms.backend.chatbot.dto.request.Message;

import java.util.List;

public record Result(
        Message message,       // 대화 메시지
        String stopReason,     // 결과 중단 이유
        int inputLength,       // 입력 토큰 수
        int outputLength,      // 응답 토큰 수
        int seed,              // 입력 seed 값
        List<String> aiFilter  // AI 필터 결과 (여기선 단순히 리스트로 표현)
) {
}