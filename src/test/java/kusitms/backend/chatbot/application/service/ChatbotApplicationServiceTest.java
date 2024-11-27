package kusitms.backend.chatbot.application.service;

import kusitms.backend.chatbot.application.dto.request.ClovaRequestDto;
import kusitms.backend.chatbot.application.dto.response.GetClovaChatbotAnswerResponseDto;
import kusitms.backend.chatbot.application.dto.response.GetGuideChatbotAnswerResponseDto;
import kusitms.backend.chatbot.application.factory.ClovaRequestFactory;
import kusitms.backend.chatbot.application.factory.MessageFactory;
import kusitms.backend.chatbot.domain.service.ChatbotApiClient;
import kusitms.backend.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ChatbotApplicationServiceTest {

    private ChatbotApplicationService service;

    @Mock
    private ChatbotApiClient chatbotApiClient;

    @Mock
    private ClovaRequestFactory clovaRequestFactory;

    @Mock
    private MessageFactory messageFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ChatbotApplicationService(chatbotApiClient, clovaRequestFactory, messageFactory);
    }

    /**
     * Clova 챗봇 응답을 성공적으로 가져오는 테스트
     */
    @Test
    void testGetClovaChatbotAnswer_Success() {
        // Given
        String userMessage = "안녕하세요!";
        ClovaRequestDto requestDto = new ClovaRequestDto(new ArrayList<>(), 0.8, 0.3, 256, 1.2);
        GetClovaChatbotAnswerResponseDto expectedResponse = GetClovaChatbotAnswerResponseDto.of("안녕하세요!");

        when(clovaRequestFactory.createClovaRequest()).thenReturn(requestDto);
        when(chatbotApiClient.requestChatbot(any(ClovaRequestDto.class)))
                .thenReturn(Mono.just("안녕하세요!"));

        // When & Then
        StepVerifier.create(service.getClovaChatbotAnswer(userMessage))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    /**
     * 유효한 카테고리 및 질문 번호로 가이드 챗봇 응답을 성공적으로 가져오는 테스트
     */
    @Test
    void testGetGuideChatbotAnswer_ValidCategory() {
        // Given
        String stadiumName = "lg";
        String categoryName = "stadium";
        int orderNumber = 1;

        // When
        GetGuideChatbotAnswerResponseDto response = service.getGuideChatbotAnswer(stadiumName, categoryName, orderNumber);

        // Then
        assertNotNull(response);
        assertEquals(null, response.imgUrl());
    }

    /**
     * 유효하지 않은 카테고리로 인해 CustomException이 발생하는 테스트
     */
    @Test
    void testGetGuideChatbotAnswer_InvalidCategory() {
        // Given
        String stadiumName = "Seoul";
        String categoryName = "invalid";
        int orderNumber = 1;

        // When & Then
        org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
            service.getGuideChatbotAnswer(stadiumName, categoryName, orderNumber);
        });
    }
}
