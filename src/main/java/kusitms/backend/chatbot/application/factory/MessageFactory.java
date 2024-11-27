package kusitms.backend.chatbot.application.factory;

import kusitms.backend.chatbot.domain.enums.Role;
import kusitms.backend.chatbot.application.dto.request.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class MessageFactory {

    @Value("${clova.prompt.baseball}")
    private String baseballPrompt;

    /**
     * 사용자 메시지를 생성합니다.
     *
     * @param content 사용자 메시지 내용
     * @return 사용자 메시지를 담은 MessageDto 객체
     */
    public MessageDto createUserMessage(String content) {
        return new MessageDto(Role.USER.getRole(), content);
    }

    /**
     * 시스템 메시지를 생성합니다.
     * 시스템 메시지는 Base64로 인코딩된 내용을 디코딩하여 생성합니다.
     *
     * @return 시스템 메시지를 담은 MessageDto 객체
     * @throws IllegalArgumentException baseballPrompt가 유효하지 않은 Base64 문자열인 경우
     */
    public MessageDto createSystemMessage() {
        try {
            return new MessageDto(Role.SYSTEM.getRole(), new String(Base64.getDecoder().decode(baseballPrompt)));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Base64로 디코딩할 수 없는 baseballPrompt 값입니다.", e);
        }
    }

    /**
     * 어시스턴트 메시지를 생성합니다.
     *
     * @param content 어시스턴트 메시지 내용
     * @return 어시스턴트 메시지를 담은 MessageDto 객체
     */
    public MessageDto createAssistantMessage(String content) {
        return new MessageDto(Role.ASSISTANT.getRole(), content);
    }
}
