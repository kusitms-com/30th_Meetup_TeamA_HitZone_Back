package kusitms.backend.chatbot.application.dto.response;

public record GetClovaChatbotAnswerResponseDto(
        String answer
) {
    public static GetClovaChatbotAnswerResponseDto of(String answer) {
        return new GetClovaChatbotAnswerResponseDto(answer);
    }
}
