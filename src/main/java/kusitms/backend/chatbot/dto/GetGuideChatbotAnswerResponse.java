package kusitms.backend.chatbot.dto;

public record GetGuideChatbotAnswerResponse(
        String[] answers,
        String imgUrl
) {
    public static GetGuideChatbotAnswerResponse of(String[] answers, String imgUrl) {
        return new GetGuideChatbotAnswerResponse(answers, imgUrl);
    }
}
