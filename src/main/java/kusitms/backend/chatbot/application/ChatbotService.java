package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.domain.enums.*;
import kusitms.backend.chatbot.dto.response.GetGuideChatbotAnswerResponseDto;
import kusitms.backend.chatbot.status.ChatbotErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    // 가이드 챗봇 답변을 조회하는 메서드
    public GetGuideChatbotAnswerResponseDto getGuideChatbotAnswer(String stadiumName, String categoryName, int orderNumber) {
        switch (categoryName) {
            case "stadium":
                return getAnswersByOrderAndStadium(orderNumber, stadiumName, StadiumGuideAnswer.values());

            case "baseball":
                return getAnswersByOrderAndStadium(orderNumber, stadiumName, BaseballGuideAnswer.values());

            case "manner":
                return getAnswersByOrderAndStadium(orderNumber, stadiumName, MannerGuideAnswer.values());

            case "facility":
                return getAnswersByOrderAndStadium(orderNumber, stadiumName, FacilityGuideAnswer.values());

            case "parking":
                return getAnswersByOrderAndStadium(orderNumber, stadiumName, ParkingGuideAnswer.values());

            default:
                throw new CustomException(ChatbotErrorStatus._IS_NOT_VALID_CATEGORY_NAME);
        }
    }

    // orderNumber와 stadiumName에 따라 답변을 찾는 공통 메서드
    private <T extends Enum<T> & GuideAnswer> GetGuideChatbotAnswerResponseDto getAnswersByOrderAndStadium(
            int orderNumber, String stadiumName, T[] guideAnswers) {

        // 1. orderNumber와 stadiumName이 모두 일치하는 답변을 찾기
        return java.util.Arrays.stream(guideAnswers)
                .filter(answer -> answer.getId() == orderNumber) // orderNumber 필터링
                .filter(answer -> stadiumName.equals(answer.getStadiumName())) // stadiumName 필터링
                .findFirst()
                .or(() ->
                        // 2. stadiumName이 null인 기본 답변을 찾기
                        java.util.Arrays.stream(guideAnswers)
                                .filter(answer -> answer.getId() == orderNumber)
                                .filter(answer -> answer.getStadiumName() == null)
                                .findFirst()
                )
                .map(answer -> GetGuideChatbotAnswerResponseDto.of(answer.getAnswers(), answer.getImgUrl()))
                // 3. 아무 답변도 없으면 예외 처리
                .orElseThrow(() -> new CustomException(ChatbotErrorStatus._NOT_FOUND_GUIDE_CHATBOT_ANSWER));
    }
}
