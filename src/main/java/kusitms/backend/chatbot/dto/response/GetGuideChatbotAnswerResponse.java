package kusitms.backend.chatbot.dto.response;

public record GetGuideChatbotAnswerResponse(
        String answer,
        String imgUrl,
        String linkName,
        String link
) {
    public static GetGuideChatbotAnswerResponse of(String answer, String imgUrl, String linkName, String link) {
        return new GetGuideChatbotAnswerResponse(answer, imgUrl, linkName, link);
    }
}
