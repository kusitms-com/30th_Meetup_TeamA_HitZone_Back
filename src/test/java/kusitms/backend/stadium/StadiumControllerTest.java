package kusitms.backend.stadium;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import jakarta.servlet.http.Cookie;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.result.application.ResultService;
import kusitms.backend.result.common.Reference;
import kusitms.backend.result.common.ReferencesGroup;
import kusitms.backend.result.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.dto.response.GetProfileResponseDto;
import kusitms.backend.result.dto.response.GetZonesResponseDto;
import kusitms.backend.result.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.presentation.ResultController;
import kusitms.backend.stadium.application.StadiumService;
import kusitms.backend.stadium.dto.response.GetZoneGuideResponseDto;
import kusitms.backend.stadium.dto.response.GetZonesNameResponseDto;
import kusitms.backend.stadium.presentation.StadiumController;
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
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StadiumController.class)
public class StadiumControllerTest extends ControllerTestConfig {

    @MockBean
    private StadiumService stadiumService;

    @Test
    @DisplayName("해당 스타디움의 구역들 이름 목록을 조회한다.")
    public void getZonesName() throws Exception {
        // given
        GetZonesNameResponseDto getZonesNameResponseDto = GetZonesNameResponseDto.of(
                List.of("레드석", "블루석", "네이비석", "오렌지석", "익사이팅석", "외야그린석", "테이블석", "프리미엄석")
        );

        Mockito.when(stadiumService.getZonesName(anyString()))
                .thenReturn(getZonesNameResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stadium/zones")
                .param("stadiumName", "잠실종합운동장")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("해당 스타디움의 구역 이름들이 조회되었습니다."))
                .andExpect(jsonPath("$.payload.names[0]").value("레드석"))
                .andDo(MockMvcRestDocumentationWrapper.document("stadium/names",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Stadium")
                                        .description("해당 스타디움의 구역 목록들을 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("스타디움명")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.names[]").description("해당 구역명")
                                        )
                                        .responseSchema(Schema.schema("GetZonesNameResponseDto"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("해당 구역에 대한 가이드 정보를 조회한다.")
    public void getZoneGuide() throws Exception {
        // given
        ReferencesGroup referencesGroup = new ReferencesGroup(
                "레드석 참고하세요.",
                List.of(
                        new Reference("시야가 중요하신 분", "외야와 가까운 쪽은 예매 시 시야 확인이 필요해요."),
                        new Reference("시끄러운 것을 좋아하지 않는 분", "오렌지석이 앞에 있어서 스피커 때문에 많이 시끄러워요.")
                )
        );
        GetZoneGuideResponseDto getZoneGuideResponseDto = new GetZoneGuideResponseDto(
                "레드석",
                "해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이에요.",
                "[1루] 2-3 Gate [3루] 2-1 Gate",
                "[1루] 약 24~30cm(10n열), 약 33~38cm(20n열)",
                "[1루] 약 25cm [3루] 약 25cm",
                "",
                "해당 구역은 비교적 조용히 경기 관람이 가능한 구역이에요.",
                List.of(referencesGroup)
        );

        Mockito.when(stadiumService.getZoneGuide(anyString(), anyString()))
                .thenReturn(getZoneGuideResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stadium/zones/guide")
                .param("stadiumName", "잠실종합운동장")
                .param("zoneName", "레드석")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("해당 구역에 대한 가이드 정보가 조회되었습니다."))
                .andExpect(jsonPath("$.payload.zoneName").value("레드석"))
                .andExpect(jsonPath("$.payload.explanation").value("해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이에요."))
                .andExpect(jsonPath("$.payload.entrance").value("[1루] 2-3 Gate [3루] 2-1 Gate"))
                .andExpect(jsonPath("$.payload.stepSpacing").value("[1루] 약 24~30cm(10n열), 약 33~38cm(20n열)"))
                .andExpect(jsonPath("$.payload.seatSpacing").value("[1루] 약 25cm [3루] 약 25cm"))
                .andExpect(jsonPath("$.payload.usageInformation").value(""))
                .andExpect(jsonPath("$.payload.tip").value("해당 구역은 비교적 조용히 경기 관람이 가능한 구역이에요."))
                .andExpect(jsonPath("$.payload.referencesGroup[0].groupTitle").value("레드석 참고하세요."))
                .andExpect(jsonPath("$.payload.referencesGroup[0].references[0].title").value("시야가 중요하신 분"))
                .andExpect(jsonPath("$.payload.referencesGroup[0].references[0].content").value("외야와 가까운 쪽은 예매 시 시야 확인이 필요해요."))
                .andDo(MockMvcRestDocumentationWrapper.document("stadium/guide",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Stadium")
                                        .description("해당 구역에 대한 구역 가이드 정보를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("스타디움명"),
                                                parameterWithName("zoneName").description("구역명")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.zoneName").description("구역명"),
                                                fieldWithPath("payload.explanation").description("구역 설명"),
                                                fieldWithPath("payload.entrance").description("구역 입구"),
                                                fieldWithPath("payload.stepSpacing").description("구역 단차 간격"),
                                                fieldWithPath("payload.seatSpacing").description("구역 좌석간 간격"),
                                                fieldWithPath("payload.usageInformation").description("구역 유용 정보"),
                                                fieldWithPath("payload.tip").description("구역 팁"),
                                                fieldWithPath("payload.referencesGroup[].groupTitle").description("구역 참고사항리스트의 제목"),
                                                fieldWithPath("payload.referencesGroup[].references[0].title").description("참고사항 제목"),
                                                fieldWithPath("payload.referencesGroup[].references[0].content").description("참고사항 내용")
                                        )
                                        .responseSchema(Schema.schema("GetZoneGuideResponseDto"))
                                        .build()
                        )
                ));
    }
}
