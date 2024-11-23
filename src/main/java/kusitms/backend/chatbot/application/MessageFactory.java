package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.domain.enums.Role;
import kusitms.backend.chatbot.dto.request.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class MessageFactory {

    @Value("${clova.prompt.baseball}")
    private String baseballPrompt;

    // 사용자 메시지 생성
    public MessageDto createUserMessage(String content) {
        return new MessageDto(Role.USER.getRole(), content);
    }

    // 시스템 메시지 생성
    public MessageDto createSystemMessage() {
        return new MessageDto(Role.SYSTEM.getRole(), new String(Base64.getDecoder().decode(baseballPrompt)));
    }

    // 어시스턴트 메시지 생성
    public MessageDto createAssistantMessage(String content) {
        return new MessageDto(Role.ASSISTANT.getRole(), content);
    }
}
