package kusitms.backend.culture;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.culture.application.FoodService;
import kusitms.backend.culture.domain.enums.Boundary;
import kusitms.backend.culture.domain.enums.Course;
import kusitms.backend.culture.dto.response.GetFoodsResponseDto;
import kusitms.backend.culture.presentation.FoodController;
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

@WebMvcTest(FoodController.class)
public class FoodControllerTest extends ControllerTestConfig {

    @MockBean
    private FoodService foodService;

    @Test
    @DisplayName("해당 구장의 먹거리 목록을 조회한다.")
    public void getSuitableFoods() throws Exception {

        GetFoodsResponseDto.FoodDto foodDto = new GetFoodsResponseDto.FoodDto("테스트이미지", Boundary.INTERIOR, Course.DESSERT, "통밥", "2층 B06 / 2.5층 C05 (1,3루 내야지정석)", List.of("김치말이국수"), "국수 7,000원 (삼겹살+국수 세트 26,000원)", "잠실야구장의 최고 인기 메뉴, 김치말이국수! 전석 매진일 기준 경기 1시간 전 주문 필요해요.");
        GetFoodsResponseDto getFoodsResponseDto = GetFoodsResponseDto.of(List.of(foodDto));

        Mockito.when(foodService.getSuitableFoods(anyString(), anyString(), anyString())).thenReturn(getFoodsResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/culture/foods")
                .param("stadiumName", "잠실종합운동장")
                .param("boundary", "내부")
                .param("course", "후식")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("구장내부 디저트류 매장 조회가 완료되었습니다."))
                .andExpect(jsonPath("$.payload.foods[0].imgUrl").value("imgUrl"))
                .andExpect(jsonPath("$.payload.foods[0].boundary").value("INTERIOR"))
                .andExpect(jsonPath("$.payload.foods[0].course").value("DESSERT"))
                .andExpect(jsonPath("$.payload.foods[0].name").value("테스트이름"))
                .andExpect(jsonPath("$.payload.foods[0].location").value("테스트위치"))
                .andExpect(jsonPath("$.payload.foods[0].menu[0]").value("테스트메뉴"))
                .andExpect(jsonPath("$.payload.foods[0].price").value("테스트가격"))
                .andExpect(jsonPath("$.payload.foods[0].explanation").value("테스트설명"))

                .andDo(MockMvcRestDocumentationWrapper.document("culture/foods",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("Culture")
                                        .description("해당 구장의 음식 목록을 조회한다.")
                                        .queryParameters(
                                                parameterWithName("stadiumName").description("구장명"),
                                                parameterWithName("boundary").description("구장 영역(내부 or 외부)"),
                                                parameterWithName("course").description("구장 코스(식사 or 후식)")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터").optional(),
                                                fieldWithPath("payload.foods[].imgUrl").description("해당 매장의 이미지 Url"),
                                                fieldWithPath("payload.foods[].boundary").description("해당 매장의 영역(내부 or 외부)"),
                                                fieldWithPath("payload.foods[].course").description("해당 매장의 코스(식사 or 후식 or 전체)"),
                                                fieldWithPath("payload.foods[].name").description("해당 매장의 이름"),
                                                fieldWithPath("payload.foods[].location").description("해당 매장의 위치"),
                                                fieldWithPath("payload.foods[].menu[]").description("해당 매장의 대표메뉴"),
                                                fieldWithPath("payload.foods[].price").description("해당 매장의 음식들 가격"),
                                                fieldWithPath("payload.foods[].explanation").description("해당 매장 설명")
                                        )
                                        .responseSchema(Schema.schema("GetFoodsResponseDto"))
                                        .build()
                        )
                ));
    }
}
