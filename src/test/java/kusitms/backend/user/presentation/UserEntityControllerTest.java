package kusitms.backend.user.presentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import jakarta.servlet.http.Cookie;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.user.application.UserApplicationService;
import kusitms.backend.user.application.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.application.dto.request.SignUpRequestDto;
import kusitms.backend.user.application.dto.response.AuthTokenResponseDto;
import kusitms.backend.user.application.dto.response.UserInfoResponseDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserEntityControllerTest extends ControllerTestConfig {

    @MockBean
    private UserApplicationService userApplicationService;

    @Test
    @DisplayName("닉네임 중복 유무를 확인한다.")
    public void checkNickname() throws Exception {

        //given
        String checkNicknameJsonRequest = """
                {
                    "nickname" : "유저 닉네임"
                }
                """;

        Mockito.doNothing().when(userApplicationService).checkNickname(any(CheckNicknameRequestDto.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/user/nickname/check")
                .content(checkNicknameJsonRequest)
                .cookie(new Cookie("registerToken", "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("사용할 수 있는 닉네임입니다."))

                .andDo(MockMvcRestDocumentationWrapper.document("nickname/check",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("User")
                                        .description("닉네임 중복 유무를 확인한다.")
                                        .requestFields(
                                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자가 입력한 닉네임")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                                        )
                                        .requestSchema(Schema.schema("CheckNicknameRequest"))
                                        .responseSchema(Schema.schema("CheckNicknameResponse"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("온보딩 정보를 토대로 회원가입을 진행한다.")
    public void signUpUser() throws Exception {

        //given
        String signUpRequestDto = """
            {
                "nickname" : "유저 닉네임"
            }
            """;

        Mockito.when(userApplicationService.signupUser(anyString(), any(SignUpRequestDto.class)))
                .thenReturn(new AuthTokenResponseDto("newAccessToken", "newRefreshToken", 3600L, 7200L));;
        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/user/signup")
                .cookie(new Cookie("registerToken", "test"))
                .content(signUpRequestDto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("201"))
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                .andDo(MockMvcRestDocumentationWrapper.document("signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("User")
                                        .description("온보딩 정보를 토대로 회원가입을 진행한다.")
                                        .requestFields(
                                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자가 입력한 닉네임")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                                        )
                                        .requestSchema(Schema.schema("SignUpRequest"))
                                        .responseSchema(Schema.schema("SignUpResponse"))
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("registerToken").description("온보딩을 위한 레지스터 토큰")
                        )
                ));
    }

    @Test
    @DisplayName("유저정보를 조회한다.")
    public void getUserInfo() throws Exception {

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto("유저 닉네임", "유저 이메일");

        Mockito.when(userApplicationService.getUserInfo(anyString()))
                .thenReturn(userInfoResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/user/info")
                .cookie(new Cookie("accessToken", "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("유저 정보 조회가 완료되었습니다."))
                .andExpect(jsonPath("$.payload.nickname").value("유저 닉네임"))
                .andExpect(jsonPath("$.payload.email").value("유저 이메일"))

                .andDo(MockMvcRestDocumentationWrapper.document("user-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("User")
                                        .description("유저의 회원정보를 조회한다.")
                                        .responseFields(
                                                fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                                fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                                fieldWithPath("payload.nickname").type(JsonFieldType.STRING).description("유저의 닉네임"),
                                                fieldWithPath("payload.email").type(JsonFieldType.STRING).description("유저의 이메일")
                                        )
                                        .responseSchema(Schema.schema("GetUserInfoResponse"))
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("accessToken").description("JWT Token")
                        )
                ));
    }

    @Test
    @DisplayName("토큰 재발급을 한다.")
    public void reIssueToken() throws Exception {

        Mockito.when(userApplicationService.reIssueToken(anyString()))
                .thenReturn(new AuthTokenResponseDto("newAccessToken", "newRefreshToken", 3600L, 7200L));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/user/token/re-issue")
                .cookie(new Cookie("refreshToken", "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("토큰 재발급에 성공하였습니다."))
                .andDo(MockMvcRestDocumentationWrapper.document("token/re-issue",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("User")
                                        .description("리프레시 토큰을 통해 액세스 토큰을 재발급합니다.")
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지")
                                        )
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("refreshToken").description("리프레시 토큰")
                        )
                ));
    }

}
