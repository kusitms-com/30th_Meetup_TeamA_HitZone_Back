package kusitms.backend.chatbot.dto.response;

public record GetClovaChatbotAnswerResponse(
        String answer
) {
    public static GetClovaChatbotAnswerResponse of(String answer) {
        return new GetClovaChatbotAnswerResponse(answer);
    }
}