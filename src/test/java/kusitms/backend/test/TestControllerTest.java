package kusitms.backend.test;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.test.application.TestService;
import kusitms.backend.test.dto.request.TestDocsRequestDto;
import kusitms.backend.test.dto.response.TestDocsResponseDto;
import kusitms.backend.test.presentation.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TestController.class)
public class TestControllerTest extends ControllerTestConfig {

    @MockBean
    private TestService testService;

    @Test
    @DisplayName("Rest-Docs 테스트 조회")
    public void testDocs() throws Exception {
        // given
        String name = "박준형이";
        String keyword = "테스트키워드";
        String testDocsJsonRequest = """
			{
			    "tip" : "테스트팁"
			}
			""";
        TestDocsResponseDto testDocsResponseDto = TestDocsResponseDto.builder()
                .keyword("테스트키워드")
                .tip("테스트팁")
                .build();
        Mockito.when(testService.testDocs(anyString(), anyString(), any(TestDocsRequestDto.class))).thenReturn(testDocsResponseDto);

        //만약 Response가 없을 경우엔 아래와 같이 실행한다.
        //Mockito.doNothing().when(testService).testDocs(anyString(), anyString(), any(TestDocsRequestDto.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/test/docs")
                .queryParam("name", name)
                .queryParam("keyword", keyword)
                .content(testDocsJsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("test/docs",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("TEST")
                                        .description("Rest Docs post 테스트용 API")
                                        .queryParameters(
                                                parameterWithName("name").description("테스트 네임"),
                                                parameterWithName("keyword").description("테스트 키워드")
                                        )
                                        .requestFields(
                                                fieldWithPath("tip").description("테스트 팁")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.keyword").description("키워드"),
                                                fieldWithPath("payload.tip").description("팁")
                                        )
                                        .responseSchema(Schema.schema("TestDocsResponseDto"))  // 여기에 추가
                                        .build()
                        )
                ));
    }
}
