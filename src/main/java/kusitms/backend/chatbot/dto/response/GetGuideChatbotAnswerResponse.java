package kusitms.backend.chatbot.dto.response;

public record GetGuideChatbotAnswerResponse(
        String answer,
        String imgUrl
) {
    public static GetGuideChatbotAnswerResponse of(String answer, String imgUrl) {
        return new GetGuideChatbotAnswerResponse(answer, imgUrl);
    }
}
