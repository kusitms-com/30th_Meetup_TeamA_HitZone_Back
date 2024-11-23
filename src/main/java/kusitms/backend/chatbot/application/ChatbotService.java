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

    /**
     * 주어진 카테고리 이름, 경기장 이름, 질문 번호에 따라 답변을 조회합니다.
     *
     * @param stadiumName  경기장 이름
     * @param categoryName 카테고리 이름 ("stadium", "baseball", "manner", "facility", "parking" 중 하나)
     * @param orderNumber  질문 번호
     * @return 가이드 챗봇 답변 데이터
     * @throws CustomException 유효하지 않은 카테고리 이름이거나 답변을 찾을 수 없는 경우
     */
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

    /**
     * 특정 질문 번호와 경기장 이름으로 답변을 조회합니다.
     * 1. 경기장 이름과 질문 번호가 모두 일치하는 답변을 먼저 찾습니다.
     * 2. 해당 조건에 맞는 답변이 없으면 경기장 이름이 없는 기본 답변을 찾습니다.
     * 3. 일치하는 답변이 없으면 예외를 발생시킵니다.
     *
     * @param orderNumber  질문 번호
     * @param stadiumName  경기장 이름
     * @param guideAnswers 답변 리스트
     * @param <T>          답변 Enum 타입
     * @return 조회된 답변 데이터
     * @throws CustomException 답변을 찾을 수 없는 경우
     */
    private <T extends Enum<T> & GuideAnswer> GetGuideChatbotAnswerResponseDto getAnswersByOrderAndStadium(
            int orderNumber, String stadiumName, T[] guideAnswers) {

        return java.util.Arrays.stream(guideAnswers)
                .filter(answer -> answer.getId() == orderNumber) // 질문 번호 필터링
                .filter(answer -> stadiumName.equals(answer.getStadiumName())) // 경기장 이름 필터링
                .findFirst()
                .or(() ->
                        java.util.Arrays.stream(guideAnswers)
                                .filter(answer -> answer.getId() == orderNumber)
                                .filter(answer -> answer.getStadiumName() == null) // 기본 답변 찾기
                                .findFirst()
                )
                .map(answer -> GetGuideChatbotAnswerResponseDto.of(answer.getAnswers(), answer.getImgUrl()))
                .orElseThrow(() -> new CustomException(ChatbotErrorStatus._NOT_FOUND_GUIDE_CHATBOT_ANSWER)); // 답변 없을 때 예외
    }
}
