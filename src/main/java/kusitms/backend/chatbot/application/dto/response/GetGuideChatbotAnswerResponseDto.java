package kusitms.backend.chatbot.application.dto.response;

public record GetGuideChatbotAnswerResponseDto(
        String answer,
        String imgUrl,
        String linkName,
        String link
) {
    public static GetGuideChatbotAnswerResponseDto of(String answer, String imgUrl, String linkName, String link) {
        return new GetGuideChatbotAnswerResponseDto(answer, imgUrl, linkName, link);
    }
}
