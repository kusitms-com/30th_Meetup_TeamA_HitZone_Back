package kusitms.backend.chatbot.dto.response;

public record GetClovaChatbotAnswerResponseDto(
        String answer
) {
    public static GetClovaChatbotAnswerResponseDto of(String answer) {
        return new GetClovaChatbotAnswerResponseDto(answer);
    }
}
