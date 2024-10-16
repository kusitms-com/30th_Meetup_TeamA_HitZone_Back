package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.domain.enums.*;
import kusitms.backend.chatbot.dto.GetGuideChatbotAnswerResponse;
import kusitms.backend.chatbot.status.ChatbotErrorStatus;
import kusitms.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    // 가이드 챗봇 답변을 조회하는 메서드
    public GetGuideChatbotAnswerResponse getGuideChatbotAnswer(String stadiumName, String categoryName, int orderNumber) {
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
    private <T extends Enum<T> & GuideAnswer> GetGuideChatbotAnswerResponse getAnswersByOrderAndStadium(
            int orderNumber, String stadiumName, T[] guideAnswers) {

        // 1. orderNumber에 맞는 enum 리스트 필터링
        List<T> matchingAnswers = new ArrayList<>();
        for (T answer : guideAnswers) {
            if (answer.getId() == orderNumber) {
                matchingAnswers.add(answer);
            }
        }

        // 2. stadiumName이 일치하는 답변 찾기
        for (T answer : matchingAnswers) {
            if (Objects.equals(answer.getStadiumName(), stadiumName)) {
                return GetGuideChatbotAnswerResponse.of(answer.getAnswers(), answer.getImgUrl());
            }
        }

        // 3. 일치하는 stadiumName이 없을 경우, 기본 응답 반환
        for (T answer : matchingAnswers) {
            if (answer.getStadiumName() == null) {
                return GetGuideChatbotAnswerResponse.of(answer.getAnswers(), answer.getImgUrl());
            }
        }

        // 4. 기본 응답도 없을 경우 예외 처리
        throw new CustomException(ChatbotErrorStatus._NOT_FOUND_GUIDE_CHATBOT_ANSWER);
    }
}