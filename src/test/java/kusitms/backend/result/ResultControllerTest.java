package kusitms.backend.result;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import jakarta.servlet.http.Cookie;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.result.application.ResultService;
import kusitms.backend.result.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.presentation.ResultController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.cookies.CookieDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResultController.class)
public class ResultControllerTest extends ControllerTestConfig {

    @MockBean
    private ResultService resultService;

    @Test
    @DisplayName("구역 추천 결과 DB 저장")
    public void saveRecommendedZonesWithAccessToken() throws Exception {
        // given
        String accessToken = "abc";
        String saveTopRankedZonesJsonRequest = """
            {
                "stadium" : "잠실종합운동장",
                "preference" : "3루석",
                "clientKeywords" : ["나 혼자", "선수들 가까이", "열정적인 응원"]
            }
            """;
        SaveTopRankedZoneResponseDto saveTopRankedZoneResponseDto = SaveTopRankedZoneResponseDto.of(1L);
        Mockito.when(resultService.saveRecommendedZones(anyString(), any(SaveTopRankedZoneRequestDto.class)))
                .thenReturn(saveTopRankedZoneResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/results/save")
                .cookie(new Cookie("accessToken", accessToken))  // 쿠키 추가
                .content(saveTopRankedZonesJsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("추천 받은 유저성향과 구역을 저장하였습니다."))
                .andExpect(jsonPath("$.payload.resultId").value(1L))
                .andDo(MockMvcRestDocumentationWrapper.document("results/save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Result")
                                        .description("구역 추천 결과를 저장한다. (accessToken 포함)")
                                        .requestFields(
                                                fieldWithPath("stadium").description("경기장 이름"),
                                                fieldWithPath("preference").description("선호 구역 (1루석 또는 3루석)"),
                                                fieldWithPath("clientKeywords").description("사용자 키워드 배열")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.resultId").description("저장된 결과ID")
                                        )
                                        .responseSchema(Schema.schema("SaveTopRankedZonesResponseDto"))
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("accessToken").description("JWT access token")
                        )
                ));
    }
}
