package kusitms.backend.chatbot.infra.adapter;

import kusitms.backend.chatbot.application.dto.request.ClovaRequestDto;
import kusitms.backend.chatbot.status.ChatbotErrorStatus;
import kusitms.backend.global.exception.CustomException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClovaApiClientTest {

    private MockWebServer mockWebServer;
    private ClovaApiClient clovaApiClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = mockWebServer.url("/").toString();
        clovaApiClient = new ClovaApiClient(WebClient.create(baseUrl));
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    /**
     * 클로바 API 호출 성공 테스트
     */
    @Test
    void testRequestChatbot_Success() {
        // Given
        String responseBody = """
            {
                "result": {
                    "message": {
                        "content": "응답 메시지"
                    }
                }
            }
        """;
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(responseBody));

        ClovaRequestDto request = new ClovaRequestDto(null, 0.8, 0.3, 256, 1.2);

        // When & Then
        StepVerifier.create(clovaApiClient.requestChatbot(request))
                .expectNext("응답 메시지")
                .verifyComplete();
    }

    /**
     * WebClient 통신 오류 발생 테스트
     */
    @Test
    void testRequestChatbot_CommunicationError() {
        // Given
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        ClovaRequestDto request = new ClovaRequestDto(null, 0.8, 0.3, 256, 1.2);

        // When & Then
        StepVerifier.create(clovaApiClient.requestChatbot(request))
                .expectErrorMatches(throwable -> throwable instanceof CustomException
                        && ((CustomException) throwable).getErrorCode() == ChatbotErrorStatus._CHATBOT_API_COMMUNICATION_ERROR)
                .verify();
    }

    /**
     * 요청 본문 확인 테스트
     */
    @Test
    void testRequestBodyMapping() throws InterruptedException {
        // Given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("""
                    {
                        "result": {
                            "message": {
                                "content": "응답 메시지"
                            }
                        }
                    }
                """));

        ClovaRequestDto request = new ClovaRequestDto(null, 0.8, 0.3, 256, 1.2);

        // When
        Mono<String> response = clovaApiClient.requestChatbot(request);

        // Then
        StepVerifier.create(response)
                .expectNext("응답 메시지")
                .verifyComplete();

        // 요청 확인
        String recordedRequest = mockWebServer.takeRequest().getBody().readUtf8();
        assertNotNull(recordedRequest);
    }
}
