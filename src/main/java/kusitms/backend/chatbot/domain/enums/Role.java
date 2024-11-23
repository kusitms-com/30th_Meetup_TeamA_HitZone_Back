package kusitms.backend.chatbot.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    SYSTEM("system"),  // 시스템
    USER("user"),    // 사용자
    ASSISTANT("assistant") // 어시스턴트
    ;

    private final String role;
}
