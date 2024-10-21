package kusitms.backend.result;

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

@WebMvcTest(ResultController.class)
public class ResultControllerTest extends ControllerTestConfig {

    @MockBean
    private ResultService resultService;

    @Test
    @DisplayName("구역 추천 결과 DB 저장")
    public void saveRecommendedZones() throws Exception {
        // given
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
                .cookie(new Cookie("accessToken", "test"))
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
                                        .description("구역 추천 결과를 저장한다. (어세스토큰은 기입/미기입 모두 가능)")
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
                                                fieldWithPath("payload.resultId").description("저장된 결과 ID")
                                        )
                                        .responseSchema(Schema.schema("SaveTopRankedZonesResponseDto"))
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
                "이러다 공까지 먹어버러",
                "야구가 참 맛있고 음식이 재밌어요",
                "야구장에서 먹는 재미까지 놓치지 않는 당신!\n야구장을 두 배로 재밌게 즐기는군요?",
                List.of("#먹으러왔는데야구도한다?", "#그래서여기구장맛있는거뭐라고?")
                );

        Mockito.when(resultService.getRecommendedProfile(anyLong()))
                .thenReturn(getProfileResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/results/profile")
                .param("resultId","2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("해당 결과의 프로필 정보를 조회하였습니다."))
                .andExpect(jsonPath("$.payload.profileId").value(1L))
                .andExpect(jsonPath("$.payload.nickname").value("이러다 공까지 먹어버러"))
                .andExpect(jsonPath("$.payload.type").value("야구가 참 맛있고 음식이 재밌어요"))
                .andExpect(jsonPath("$.payload.explanation").value("야구장에서 먹는 재미까지 놓치지 않는 당신!\n야구장을 두 배로 재밌게 즐기는군요?"))
                .andExpect(jsonPath("$.payload.hashTags[0]").value("#먹으러왔는데야구도한다?"))
                .andExpect(jsonPath("$.payload.hashTags[1]").value("#그래서여기구장맛있는거뭐라고?"))
                .andDo(MockMvcRestDocumentationWrapper.document("results/profile",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Result")
                                        .description("해당 결과의 유저 프로필 정보를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("resultId").description("조회할 결과의 ID")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.profileId").description("해당 결과의 프로필 ID"),
                                                fieldWithPath("payload.nickname").description("해당 결과의 프로필의 닉네임"),
                                                fieldWithPath("payload.type").description("해당 결과의 프로필의 타입"),
                                                fieldWithPath("payload.explanation").description("해당 결과의 프로필 설명"),
                                                fieldWithPath("payload.hashTags[]").description("해당 결과의 프로필의 해시태그")
                                        )
                                        .responseSchema(Schema.schema("GetProfileResponseDto"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("해당 결과의 추천구역 리스트 조회")
    public void getRecommendedZones() throws Exception {
        // given
        GetZonesResponseDto.ZoneResponseDto redZone = new GetZonesResponseDto.ZoneResponseDto(
                1L,
                "레드석",
                List.of("응원도 적당히 즐길 수 있지만, 야구나 함께 온 동행자와의 대화에도 집중할 수 있는 구역이에요!"),
                "해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이예요.",
                List.of(
                        new ReferencesGroup(
                                "레드석 참고하세요.",
                                List.of(
                                        new Reference("시야가 중요하신 분", "외야와 가까운 쪽은 예매 시 시야 확인이 필요해요. 기둥이나 그물망으로 시야 방해를 받을 수 있어요!"),
                                        new Reference("시끄러운 것을 좋아하지 않는 분", "오렌지석이 앞에 있어서 스피커 때문에 많이 시끄러워요. 조용한 관람을 원하시면 다른 구역을 추천해요!")
                                )
                        )
                )
        );
        GetZonesResponseDto.ZoneResponseDto blueZone = new GetZonesResponseDto.ZoneResponseDto(
                2L,
                "블루석",
                List.of("힘차게 응원도 가능하고, 야구에 집중도 할 수 있는 구역이에요!"),
                "해당 구역은 비교적 조용히 경기 관람이 가능한 구역이예요.",
                List.of(
                        new ReferencesGroup(
                                "블루석 참고하세요.",
                                List.of(
                                        new Reference("시야가 중요하신 분", "홈과 가까운 쪽은 예매 시 시야 확인이 필요해요. 그물망으로 인해 시야 방해를 받을 수 있어요!"),
                                        new Reference("적당한 응원을 하고 싶으신 분", "비교적 조용히 경기를 관람하고 적당히 응원할 수 있는 곳이에요. 열정적인 응원을 하고 싶으신 분들은 다른 구역을 더 추천해요!")
                                )
                        )
                )
        );
        GetZonesResponseDto getZonesResponseDto = GetZonesResponseDto.of(List.of(redZone, blueZone));
        Mockito.when(resultService.getRecommendedZones(anyLong(), anyLong()))
                .thenReturn(getZonesResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/results/zones")
                .param("resultId","2")
                .param("count", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("해당 결과의 추천 구역 정보 리스트를 조회하였습니다."))
                .andExpect(jsonPath("$.payload.zones[0].zoneId").value(redZone.zoneId()))
                .andExpect(jsonPath("$.payload.zones[0].name").value(redZone.name()))
                .andExpect(jsonPath("$.payload.zones[0].explanations[0]").value(redZone.explanations().get(0)))
                .andExpect(jsonPath("$.payload.zones[0].tip").value(redZone.tip()))
                .andExpect(jsonPath("$.payload.zones[0].referencesGroup[0].groupTitle").value(redZone.referencesGroup().get(0).getGroupTitle()))
                .andExpect(jsonPath("$.payload.zones[0].referencesGroup[0].references[0].title").value(redZone.referencesGroup().get(0).getReferences().get(0).getTitle()))
                .andExpect(jsonPath("$.payload.zones[0].referencesGroup[0].references[0].content").value(redZone.referencesGroup().get(0).getReferences().get(0).getContent()))
                .andDo(MockMvcRestDocumentationWrapper.document("results/zones",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Result")
                                        .description("해당 결과의 추천 구역 리스트를 조회한다.")
                                        .queryParameters(
                                                parameterWithName("resultId").description("조회할 결과의 ID"),
                                                parameterWithName("count").description("조회할 결과에 대한 추천구역의 개수")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.zones[].zoneId").description("구역 ID"),
                                                fieldWithPath("payload.zones[].name").description("구역 이름"),
                                                fieldWithPath("payload.zones[].explanations[]").description("구역 설명"),
                                                fieldWithPath("payload.zones[].tip").description("구역에 대한 팁"),
                                                fieldWithPath("payload.zones[].referencesGroup[].groupTitle").description("참고 그룹 제목"),
                                                fieldWithPath("payload.zones[].referencesGroup[].references[].title").description("참고 항목 제목"),
                                                fieldWithPath("payload.zones[].referencesGroup[].references[].content").description("참고 항목 내용")
                                        )
                                        .responseSchema(Schema.schema("GetZonesResponseDto"))
                                        .build()
                        )
                ));
    }
}
