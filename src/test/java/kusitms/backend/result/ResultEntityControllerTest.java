package kusitms.backend.result;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import jakarta.servlet.http.Cookie;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.result.application.ResultApplicationService;
import kusitms.backend.result.application.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.application.dto.response.GetProfileResponseDto;
import kusitms.backend.result.application.dto.response.GetZonesResponseDto;
import kusitms.backend.result.application.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.domain.value.Reference;
import kusitms.backend.result.domain.value.ReferencesGroup;
import kusitms.backend.result.presentation.ResultController;
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
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResultController.class)
public class ResultEntityControllerTest extends ControllerTestConfig {

    @MockBean
    private ResultApplicationService resultApplicationService;

    @Test
    @DisplayName("구역 추천 결과 DB 저장")
    public void saveRecommendedZones() throws Exception {
        // given
        String saveTopRankedZonesJsonRequest = """
            {
                "stadium" : "잠실종합운동장 (잠실)",
                "preference" : "3루석",
                "clientKeywords" : ["나 혼자", "선수들 가까이", "열정적인 응원"]
            }
            """;
        SaveTopRankedZoneResponseDto saveTopRankedZoneResponseDto = SaveTopRankedZoneResponseDto.of(1L);

        Mockito.when(resultApplicationService.saveRecommendedResult(anyString(), any(SaveTopRankedZoneRequestDto.class)))
                .thenReturn(saveTopRankedZoneResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/results/save")
                .cookie(new Cookie("accessToken", "test"))
                .content(saveTopRankedZonesJsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("추천 받은 유저성향과 구역을 저장하였습니다."))
                .andExpect(jsonPath("$.payload.resultId").value(1L))
                .andDo(MockMvcRestDocumentationWrapper.document("results/save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Result")
                                        .description("구역 추천 결과를 저장한다. (accessToken은 기입/미기입 모두 가능)")
                                        .requestFields(
                                                fieldWithPath("stadium").type(JsonFieldType.STRING).description("경기장 이름 [예시 : 잠실종합운동장 (잠실)]"),
                                                fieldWithPath("preference").type(JsonFieldType.STRING).description("선호 구역 [예시 : 1루석 또는 3루석]"),
                                                fieldWithPath("clientKeywords[]").type(JsonFieldType.ARRAY).description("사용자 키워드 배열")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                                fieldWithPath("payload.resultId").type(JsonFieldType.NUMBER).description("저장된 결과 ID")
                                        )
                                        .requestSchema(Schema.schema("SaveTopRankedZoneRequestDto"))
                                        .responseSchema(Schema.schema("SaveTopRankedZoneResponseDto"))
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("accessToken").description("JWT access token")
                        )
                ));
    }

    @Test
    @DisplayName("해당 결과의 프로필 조회")
    public void getRecommendedProfile() throws Exception {
        // given
        GetProfileResponseDto getProfileResponseDto = new GetProfileResponseDto(
                1L,
                "https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/eating.svg",
                "이러다 공까지 먹어버러",
                "야구가 참 맛있고 음식이 재밌어요",
                List.of( "#야구장미식가", "#먹으러왔는데야구도한다?")
        );

        Mockito.when(resultApplicationService.getRecommendedProfile(anyLong()))
                .thenReturn(getProfileResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/results/profile")
                .param("resultId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("해당 결과의 프로필 정보를 조회하였습니다."))
                .andExpect(jsonPath("$.payload.profileId").value(1L))
                .andExpect(jsonPath("$.payload.imgUrl").value("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/eating.svg"))
                .andExpect(jsonPath("$.payload.nickname").value("이러다 공까지 먹어버러"))
                .andExpect(jsonPath("$.payload.type").value("야구가 참 맛있고 음식이 재밌어요"))
                .andExpect(jsonPath("$.payload.hashTags[0]").value("#야구장미식가"))
                .andExpect(jsonPath("$.payload.hashTags[1]").value("#먹으러왔는데야구도한다?"))
                .andDo(MockMvcRestDocumentationWrapper.document("results/profile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Result")
                                        .description("해당 결과의 유저 프로필 정보를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("resultId").description("조회할 결과의 ID [예시 : 1 (NUMBER type)]")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                                fieldWithPath("payload.profileId").type(JsonFieldType.NUMBER).description("해당 결과의 프로필 ID"),
                                                fieldWithPath("payload.imgUrl").type(JsonFieldType.STRING).description("해당 결과의 이미지 URL"),
                                                fieldWithPath("payload.nickname").type(JsonFieldType.STRING).description("해당 결과의 프로필 닉네임"),
                                                fieldWithPath("payload.type").type(JsonFieldType.STRING).description("해당 결과의 프로필 타입"),
                                                fieldWithPath("payload.hashTags[]").type(JsonFieldType.ARRAY).description("해당 결과의 프로필 해시태그 리스트")
                                        )
                                        .responseSchema(Schema.schema("GetProfileResponseDto"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("해당 결과의 추천 구역 리스트 조회")
    public void getRecommendedZones() throws Exception {
        // given
        GetZonesResponseDto.ZoneResponseDto redZone = new GetZonesResponseDto.ZoneResponseDto(
                1L,
                "레드석",
                "#DC032A",
                List.of("응원도 적당히 즐길 수 있지만, 야구나 함께 온 동행자와의 대화에도 집중할 수 있는 구역이에요!"),
                "해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이예요.",
                List.of(
                        new ReferencesGroup(
                                "레드석 참고하세요.",
                                List.of(
                                        new Reference("시야가 중요하신 분",
                                                new String[]{"외야와 가까운 쪽은 예매 시 시야 확인이 필요해요."}),
                                        new Reference("시끄러운 것을 좋아하지 않는 분",
                                                new String[]{"오렌지석이 앞에 있어서 스피커 때문에 많이 시끄러워요."})
                                )
                        )
                )
        );

        GetZonesResponseDto.ZoneResponseDto blueZone = new GetZonesResponseDto.ZoneResponseDto(
                2L,
                "블루석",
                "#4699F2",
                List.of("힘차게 응원도 가능하고, 야구에 집중도 할 수 있는 구역이에요!"),
                "해당 구역은 비교적 조용히 경기 관람이 가능한 구역이에요.",
                List.of(
                        new ReferencesGroup(
                                "블루석 참고하세요.",
                                List.of(
                                        new Reference("시야가 중요하신 분",
                                                new String[]{"홈과 가까운 쪽은 예매 시 시야 확인이 필요해요. 그물망으로 인해 시야 방해를 받을 수 있어요!"}),
                                        new Reference("적당한 응원을 하고 싶으신 분",
                                                new String[]{"비교적 조용히 경기를 관람하고 적당히 응원할 수 있는 곳이에요. 열정적인 응원을 하고 싶으신 분들은 다른 구역을 더 추천해요!"})
                                )
                        )
                )
        );

        GetZonesResponseDto getZonesResponseDto = GetZonesResponseDto.of(List.of(redZone, blueZone));

        Mockito.when(resultApplicationService.getRecommendedZones(anyLong(), anyLong()))
                .thenReturn(getZonesResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/results/zones")
                .param("resultId", "2")
                .param("count", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("해당 결과의 추천 구역 정보 리스트를 조회하였습니다."))
                .andExpect(jsonPath("$.payload.zones[0].zoneId").value(redZone.zoneId()))
                .andExpect(jsonPath("$.payload.zones[0].name").value(redZone.name()))
                .andExpect(jsonPath("$.payload.zones[0].color").value(redZone.color()))
                .andExpect(jsonPath("$.payload.zones[0].explanations[0]").value(redZone.explanations().get(0)))
                .andExpect(jsonPath("$.payload.zones[0].tip").value(redZone.tip()))
                .andExpect(jsonPath("$.payload.zones[0].referencesGroup[0].groupTitle").value(redZone.referencesGroup().get(0).getGroupTitle()))
                .andExpect(jsonPath("$.payload.zones[0].referencesGroup[0].references[0].title").value(redZone.referencesGroup().get(0).getReferences().get(0).getTitle()))
                .andExpect(jsonPath("$.payload.zones[0].referencesGroup[0].references[0].contents[0]").value(redZone.referencesGroup().get(0).getReferences().get(0).getContents()[0]))

                .andExpect(jsonPath("$.payload.zones[1].zoneId").value(blueZone.zoneId()))
                .andExpect(jsonPath("$.payload.zones[1].name").value(blueZone.name()))
                .andExpect(jsonPath("$.payload.zones[1].color").value(blueZone.color()))
                .andExpect(jsonPath("$.payload.zones[1].explanations[0]").value(blueZone.explanations().get(0)))
                .andExpect(jsonPath("$.payload.zones[1].tip").value(blueZone.tip()))
                .andExpect(jsonPath("$.payload.zones[1].referencesGroup[0].groupTitle").value(blueZone.referencesGroup().get(0).getGroupTitle()))
                .andExpect(jsonPath("$.payload.zones[1].referencesGroup[0].references[0].title").value(blueZone.referencesGroup().get(0).getReferences().get(0).getTitle()))
                .andExpect(jsonPath("$.payload.zones[1].referencesGroup[0].references[0].contents[0]").value(blueZone.referencesGroup().get(0).getReferences().get(0).getContents()[0]))

                .andDo(MockMvcRestDocumentationWrapper.document("results/zones",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Result")
                                        .description("해당 결과의 추천 구역 리스트를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("resultId").description("조회할 결과의 ID [예시 : 2 (NUMBER type)]"),
                                                parameterWithName("count").description("조회할 추천 구역의 개수 [예시 : 2 (NUMBER type)]")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                                                fieldWithPath("payload.zones[].zoneId").type(JsonFieldType.NUMBER).description("구역 ID"),
                                                fieldWithPath("payload.zones[].name").type(JsonFieldType.STRING).description("구역 이름"),
                                                fieldWithPath("payload.zones[].color").type(JsonFieldType.STRING).description("구역 색상"),
                                                fieldWithPath("payload.zones[].explanations[]").type(JsonFieldType.ARRAY).description("구역 설명 리스트"),
                                                fieldWithPath("payload.zones[].tip").type(JsonFieldType.STRING).description("구역에 대한 팁"),
                                                fieldWithPath("payload.zones[].referencesGroup[].groupTitle").type(JsonFieldType.STRING).description("참고 그룹 제목"),
                                                fieldWithPath("payload.zones[].referencesGroup[].references[].title").type(JsonFieldType.STRING).description("참고 항목 제목"),
                                                fieldWithPath("payload.zones[].referencesGroup[].references[].contents").type(JsonFieldType.ARRAY).description("참고 항목 내용")
                                        )
                                        .responseSchema(Schema.schema("GetZonesResponseDto"))
                                        .build()
                        )
                ));
    }
}