package kusitms.backend.culture;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.culture.application.EntertainmentService;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.dto.response.GetEntertainmentsResponseDto;
import kusitms.backend.culture.presentation.EntertainmentController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EntertainmentController.class)
public class EntertainmentControllerTest extends ControllerTestConfig {

    @MockBean
    private EntertainmentService entertainmentService;

    @Test
    @DisplayName("해당 구장의 즐길거리 목록을 조회한다.")
    public void getSuitableEntertainments() throws Exception {

        GetEntertainmentsResponseDto.EntertainmentDto entertainmentDto = new GetEntertainmentsResponseDto.EntertainmentDto("테스트이미지", Boundary.INTERIOR, "포토카드", "선수들의 사진을 뽑을 수 있는 포토카드! 경기 시작 전에 포토카드 기계로 가서 포토카드를 뽑을 수 있어요.","기계의 QR을 통해 원하는 선수나, 자신의 사진으로 커스텀 포토카드를 뽑을 수 있으니 참고하세요!");
        GetEntertainmentsResponseDto getEntertainmentsResponseDto = GetEntertainmentsResponseDto.of(List.of(entertainmentDto));

        Mockito.when(entertainmentService.getSuitableEntertainments(anyString(), anyString())).thenReturn(getEntertainmentsResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/culture/entertainments")
                .param("stadiumName", "잠실종합운동장")
                .param("boundary", "내부")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("구장내부 즐길거리 조회가 완료되었습니다."))
                .andExpect(jsonPath("$.payload.entertainments[0].imgUrl").value("테스트이미지"))
                .andExpect(jsonPath("$.payload.entertainments[0].boundary").value("INTERIOR"))
                .andExpect(jsonPath("$.payload.entertainments[0].name").value("포토카드"))
                .andExpect(jsonPath("$.payload.entertainments[0].explanation").value("선수들의 사진을 뽑을 수 있는 포토카드! 경기 시작 전에 포토카드 기계로 가서 포토카드를 뽑을 수 있어요."))
                .andExpect(jsonPath("$.payload.entertainments[0].tip").value("기계의 QR을 통해 원하는 선수나, 자신의 사진으로 커스텀 포토카드를 뽑을 수 있으니 참고하세요!"))

                .andDo(MockMvcRestDocumentationWrapper.document("culture/entertainments",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Culture")
                                        .description("해당 구장의 즐길거리 목록을 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("구장명"),
                                                parameterWithName("boundary").description("구장 영역(내부 or 외부)")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.entertainments[].imgUrl").description("해당 즐길거리의 이미지 Url"),
                                                fieldWithPath("payload.entertainments[].boundary").description("해당 즐길거리의 영역(내부 or 외부)"),
                                                fieldWithPath("payload.entertainments[].name").description("해당 즐길거리의 이름"),
                                                fieldWithPath("payload.entertainments[].explanation").description("해당 즐길거리의 설명"),
                                                fieldWithPath("payload.entertainments[].tip").description("해당 즐길거리의 팁")

                                        )
                                        .responseSchema(Schema.schema("GetEntertainmentsResponseDto"))
                                        .build()
                        )
                ));
    }
}
