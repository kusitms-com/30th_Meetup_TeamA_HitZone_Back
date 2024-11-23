package kusitms.backend.chatbot.dto.response;

public record GetGuideChatbotAnswerResponseDto(
        String[] answers,
        String imgUrl
) {
    public static GetGuideChatbotAnswerResponseDto of(String[] answers, String imgUrl) {
        return new GetGuideChatbotAnswerResponseDto(answers, imgUrl);
    }
}
