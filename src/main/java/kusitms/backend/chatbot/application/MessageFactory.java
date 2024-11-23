package kusitms.backend.chatbot.application;

import kusitms.backend.chatbot.domain.enums.Role;
import kusitms.backend.chatbot.dto.request.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class MessageFactory {

    @Value("${clova.prompt.baseball}")
    private String baseballPrompt;

    // 사용자 메시지 생성
    public Message createUserMessage(String content) {
        return new Message(Role.USER.getRole(), content);
    }

    // 시스템 메시지 생성
    public Message createSystemMessage() {
        return new Message(Role.SYSTEM.getRole(), new String(Base64.getDecoder().decode(baseballPrompt)));
    }

    // 어시스턴트 메시지 생성
    public Message createAssistantMessage(String content) {
        return new Message(Role.ASSISTANT.getRole(), content);
    }
}
