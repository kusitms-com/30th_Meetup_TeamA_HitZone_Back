package kusitms.backend.stadium.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.result.domain.value.Reference;
import kusitms.backend.result.domain.value.ReferencesGroup;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.application.dto.response.GetStadiumInfosResponseDto;
import kusitms.backend.stadium.application.dto.response.GetZoneGuideResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StadiumController.class)
public class StadiumControllerTest extends ControllerTestConfig {

    @MockBean
    private StadiumApplicationService stadiumApplicationService;

    @Test
    @DisplayName("해당 스타디움의 정보를 조회한다.")
    public void getZonesName() throws Exception {
        // given
        GetStadiumInfosResponseDto getStadiumInfosResponseDto = GetStadiumInfosResponseDto.of(
                "https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/guide_home_lg.svg",
                "두산베어스, LG 트윈스",
                "상대팀",
                List.of(
                        new GetStadiumInfosResponseDto.ZoneInfo("레드석", "#DC032A"),
                        new GetStadiumInfosResponseDto.ZoneInfo("블루석", "#4699F2"),
                        new GetStadiumInfosResponseDto.ZoneInfo("네이비석", "#242953"),
                        new GetStadiumInfosResponseDto.ZoneInfo("오렌지석", "#E16900"),
                        new GetStadiumInfosResponseDto.ZoneInfo("익사이팅석", "#6D6D6D"),
                        new GetStadiumInfosResponseDto.ZoneInfo("외야그린석", "#339600"),
                        new GetStadiumInfosResponseDto.ZoneInfo("테이블석", "#7C0065"),
                        new GetStadiumInfosResponseDto.ZoneInfo("프리미엄석", "#185DDD")
                )
        );

        Mockito.when(stadiumApplicationService.getStadiumInfos(anyString()))
                .thenReturn(getStadiumInfosResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stadium/zones")
                .param("stadiumName", "잠실종합운동장 (잠실)")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("해당 스타디움의 정보가 조회되었습니다."))
                .andExpect(jsonPath("$.payload.imgUrl").value("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/guide_home_lg.svg"))
                .andExpect(jsonPath("$.payload.firstBaseSide").value("두산베어스, LG 트윈스"))
                .andExpect(jsonPath("$.payload.thirdBaseSide").value("상대팀"))
                .andExpect(jsonPath("$.payload.zones[0].zoneName").value("레드석"))
                .andExpect(jsonPath("$.payload.zones[0].zoneColor").value("#DC032A"))
                .andDo(MockMvcRestDocumentationWrapper.document("stadium/names",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Stadium")
                                        .description("해당 스타디움의 정보를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("스타디움명 [예시 : 잠실종합운동장 (잠실)]")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                                fieldWithPath("payload.imgUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                                fieldWithPath("payload.firstBaseSide").type(JsonFieldType.STRING).description("1루석 설명"),
                                                fieldWithPath("payload.thirdBaseSide").type(JsonFieldType.STRING).description("3루석 설명"),
                                                fieldWithPath("payload.zones[]").type(JsonFieldType.ARRAY).description("구역 정보 리스트"),
                                                fieldWithPath("payload.zones[].zoneName").type(JsonFieldType.STRING).description("구역 이름"),
                                                fieldWithPath("payload.zones[].zoneColor").type(JsonFieldType.STRING).description("구역 색상 코드")
                                        )
                                        .responseSchema(Schema.schema("GetStadiumInfosResponseDto"))
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
                        new Reference("시야가 중요하신 분",
                                new String[]{"외야와 가까운 쪽은 예매 시 시야 확인이 필요해요."}),
                        new Reference("시끄러운 것을 좋아하지 않는 분",
                                new String[]{"오렌지석이 앞에 있어서 스피커 때문에 많이 시끄러워요."})
                )
        );
        GetZoneGuideResponseDto getZoneGuideResponseDto = new GetZoneGuideResponseDto(
                "https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/red.svg",
                "레드석",
                "#DC032A",
                "해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이에요.",
                "두산베어스, LG 트윈스",
                "상대팀",
                new String[]{"[1루] 2-3 Gate [3루] 2-1 Gate"},
                new String[]{"[1루] 약 24~30cm(10n열), 약 33~38cm(20n열)"},
                new String[]{"[1루] 약 25cm [3루] 약 25cm"},
                new String[]{""},
                "해당 구역은 비교적 조용히 경기 관람이 가능한 구역이에요.",
                List.of(referencesGroup)
        );

        Mockito.when(stadiumApplicationService.getZoneGuide(anyString(), anyString()))
                .thenReturn(getZoneGuideResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/stadium/zones/guide")
                .param("stadiumName", "잠실종합운동장 (잠실)")
                .param("zoneName", "레드석")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("해당 구역에 대한 가이드 정보가 조회되었습니다."))
                .andExpect(jsonPath("$.payload.imgUrl").value("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/red.svg"))
                .andExpect(jsonPath("$.payload.zoneName").value("레드석"))
                .andExpect(jsonPath("$.payload.zoneColor").value("#DC032A"))
                .andExpect(jsonPath("$.payload.explanation").value("해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이에요."))
                .andExpect(jsonPath("$.payload.firstBaseSide").value("두산베어스, LG 트윈스"))
                .andExpect(jsonPath("$.payload.thirdBaseSide").value("상대팀"))
                .andExpect(jsonPath("$.payload.entrance").isArray())
                .andExpect(jsonPath("$.payload.entrance[0]").value("[1루] 2-3 Gate [3루] 2-1 Gate"))
                .andExpect(jsonPath("$.payload.stepSpacing").isArray())
                .andExpect(jsonPath("$.payload.stepSpacing[0]").value("[1루] 약 24~30cm(10n열), 약 33~38cm(20n열)"))
                .andExpect(jsonPath("$.payload.seatSpacing").isArray())
                .andExpect(jsonPath("$.payload.seatSpacing[0]").value("[1루] 약 25cm [3루] 약 25cm"))
                .andExpect(jsonPath("$.payload.usageInformation").isArray())
                .andExpect(jsonPath("$.payload.usageInformation[0]").value(""))
                .andExpect(jsonPath("$.payload.tip").value("해당 구역은 비교적 조용히 경기 관람이 가능한 구역이에요."))
                .andExpect(jsonPath("$.payload.referencesGroup[0].groupTitle").value("레드석 참고하세요."))
                .andExpect(jsonPath("$.payload.referencesGroup[0].references[0].title").value("시야가 중요하신 분"))
                .andExpect(jsonPath("$.payload.referencesGroup[0].references[0].contents[0]").value("외야와 가까운 쪽은 예매 시 시야 확인이 필요해요."))
                .andDo(MockMvcRestDocumentationWrapper.document("stadium/guide",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Stadium")
                                        .description("해당 구역에 대한 구역 가이드 정보를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("스타디움명 [예시 : 잠실종합운동장 (잠실)]"),
                                                parameterWithName("zoneName").description("구역명 [예시 : 레드석]")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                                fieldWithPath("payload.imgUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                                fieldWithPath("payload.zoneName").type(JsonFieldType.STRING).description("구역명"),
                                                fieldWithPath("payload.zoneColor").type(JsonFieldType.STRING).description("구역 색상"),
                                                fieldWithPath("payload.explanation").type(JsonFieldType.STRING).description("구역 설명"),
                                                fieldWithPath("payload.firstBaseSide").type(JsonFieldType.STRING).description("1루석 설명"),
                                                fieldWithPath("payload.thirdBaseSide").type(JsonFieldType.STRING).description("3루석 설명"),
                                                fieldWithPath("payload.entrance").type(JsonFieldType.ARRAY).description("구역 입구"),
                                                fieldWithPath("payload.stepSpacing").type(JsonFieldType.ARRAY).description("구역 단차 간격"),
                                                fieldWithPath("payload.seatSpacing").type(JsonFieldType.ARRAY).description("구역 좌석간 간격"),
                                                fieldWithPath("payload.usageInformation").type(JsonFieldType.ARRAY).description("구역 유용 정보"),
                                                fieldWithPath("payload.tip").type(JsonFieldType.STRING).description("구역 팁"),
                                                fieldWithPath("payload.referencesGroup[].groupTitle").type(JsonFieldType.STRING).description("구역 참고사항리스트의 제목"),
                                                fieldWithPath("payload.referencesGroup[].references[].title").type(JsonFieldType.STRING).description("참고사항 제목"),
                                                fieldWithPath("payload.referencesGroup[].references[].contents").type(JsonFieldType.ARRAY).description("참고사항 내용")
                                        )
                                        .responseSchema(Schema.schema("GetZoneGuideResponseDto"))
                                        .build()
                        )
                ));
    }
}
