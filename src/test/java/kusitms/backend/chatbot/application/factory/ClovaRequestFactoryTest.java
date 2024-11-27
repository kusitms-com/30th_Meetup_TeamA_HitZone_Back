package kusitms.backend.chatbot.application.factory;

import kusitms.backend.chatbot.application.dto.request.ChatbotRequestDto;
import kusitms.backend.chatbot.application.dto.request.ClovaRequestDto;
import kusitms.backend.chatbot.application.dto.request.MessageDto;
import kusitms.backend.chatbot.domain.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ClovaRequestFactoryTest {

    @InjectMocks
    private ClovaRequestFactory clovaRequestFactory;

    @Mock
    private MessageFactory messageFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * ClovaRequestFactory의 createClovaRequest 메서드 테스트
     */
    @Test
    void testCreateClovaRequest() {
        // Given
        MessageDto systemMessage = new MessageDto(Role.SYSTEM.getRole(), "System message content");
        when(messageFactory.createSystemMessage()).thenReturn(systemMessage);

        // When
        ChatbotRequestDto chatbotRequest = clovaRequestFactory.createClovaRequest();

        // Then
        assertNotNull(chatbotRequest);
        assertEquals(1, chatbotRequest.getMessages().size());
        assertEquals(systemMessage, chatbotRequest.getMessages().get(0));

        ClovaRequestDto clovaRequest = (ClovaRequestDto) chatbotRequest;
        assertEquals(0.8, clovaRequest.topP());
        assertEquals(0.3, clovaRequest.temperature());
        assertEquals(256, clovaRequest.maxTokens());
        assertEquals(5.0, clovaRequest.repeatPenalty());
    }
}
