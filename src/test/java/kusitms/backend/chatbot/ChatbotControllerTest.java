package kusitms.backend.chatbot;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import kusitms.backend.chatbot.application.ChatbotService;
import kusitms.backend.chatbot.application.ClovaService;
import kusitms.backend.chatbot.dto.response.GetClovaChatbotAnswerResponse;
import kusitms.backend.chatbot.dto.response.GetGuideChatbotAnswerResponse;
import kusitms.backend.chatbot.presentation.ChatbotController;
import kusitms.backend.configuration.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatbotController.class)
public class ChatbotControllerTest extends ControllerTestConfig {

    @MockBean
    private ChatbotService chatbotService;

    @MockBean
    private ClovaService clovaService;

    @Test
    @DisplayName("가이드 챗봇 답변 조회")
    public void getGuideChatbotAnswer() throws Exception {
        // given
        GetGuideChatbotAnswerResponse response = new GetGuideChatbotAnswerResponse(new String[]{
                "각 구장에 위치한 굿즈샵에서 원하는 응원 도구를 구매할 수 있어요!",

                "잠실 야구장의 경우, 지하철 2호선 '종합운동장역' 6번 출구 앞에 위치한 야구 용품샵 '유니크 스포츠'를 이용할 수 있어요! 홈팀인 엘지 트윈스와 두산 베어스의 굿즈 뿐만 아니라, 원정팀들의 굿즈도 있으니 한 번 방문해보세요!",

                "종합운동장역을 나가기 전, 역사에 위치한 ‘라커디움파크 종합운동장역점’에서도 굿즈를 판매 중이에요!"
        }, null);

        Mockito.when(chatbotService.getGuideChatbotAnswer(anyString(), anyString(), anyInt()))
                .thenReturn(response);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/chatbot/guide")
                .param("stadiumName", "lg")
                .param("categoryName", "stadium")
                .param("orderNumber", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("가이드 챗봇 답변을 가져오는 데 성공했습니다."))
                .andExpect(jsonPath("$.payload.answers[0]").value("각 구장에 위치한 굿즈샵에서 원하는 응원 도구를 구매할 수 있어요!"))
                .andExpect(jsonPath("$.payload.imgUrl").isEmpty())

                // docs
                .andDo(MockMvcRestDocumentationWrapper.document("chatbot/guide",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Chatbot")
                                        .description("가이드 챗봇 답변을 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("경기장 이름"),
                                                parameterWithName("categoryName").description("카테고리 이름"),
                                                parameterWithName("orderNumber").description("질문 번호")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("payload.answers").type(JsonFieldType.ARRAY).description("답변 목록"),
                                                fieldWithPath("payload.imgUrl").type(JsonFieldType.STRING).description("이미지 URL").optional()
                                        )
                                        .responseSchema(Schema.schema("GetGuideChatbotAnswerResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("Clova 챗봇 답변 조회")
    public void getClovaChatbotAnswer() throws Exception {
        // given
        GetClovaChatbotAnswerResponse response = new GetClovaChatbotAnswerResponse("안녕하세요! 저는 야구 가이드 챗봇 '루키'에요! 야구에 대한 궁금한 점이 있다면 언제든지 물어봐 주세요!");

        Mockito.when(clovaService.getClovaChatbotAnswer(anyString()))
                .thenReturn(response);

        String clovaChatbotAnswerJsonRequest = """
            {
                "message": "너 누구야?"
            }
            """;

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/chatbot/clova")
                .content(clovaChatbotAnswerJsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("네이버 클로바 챗봇 답변을 가져오는 데 성공했습니다."))
                .andExpect(jsonPath("$.payload.answer").value("안녕하세요! 저는 야구 가이드 챗봇 '루키'에요! 야구에 대한 궁금한 점이 있다면 언제든지 물어봐 주세요!"))

                // docs
                .andDo(MockMvcRestDocumentationWrapper.document("chatbot/clova",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Chatbot")
                                        .description("Clova 챗봇 답변을 조회한다.")
                                        .requestFields(
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("사용자가 보낸 메시지")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("payload.answer").type(JsonFieldType.STRING).description("Clova 챗봇의 답변")
                                        )
                                        .requestSchema(Schema.schema("GetClovaChatbotAnswerRequest"))
                                        .responseSchema(Schema.schema("GetClovaChatbotAnswerResponse"))
                                        .build()
                        )
                ));
    }
}