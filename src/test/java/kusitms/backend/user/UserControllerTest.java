package kusitms.backend.user;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import jakarta.servlet.http.Cookie;
import kusitms.backend.configuration.ControllerTestConfig;
import kusitms.backend.user.application.UserService;
import kusitms.backend.user.dto.request.CheckNicknameRequestDto;
import kusitms.backend.user.dto.request.SignUpRequestDto;
import kusitms.backend.user.dto.response.UserInfoResponseDto;
import kusitms.backend.user.presentation.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
public class UserControllerTest extends ControllerTestConfig {

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저정보를 조회한다.")
    public void checkNickname() throws Exception {

        //given
        String checkNicknameJsonRequest = """
                {
                    "nickname" : "유저 닉네임"
                }
                """;

        Mockito.doNothing().when(userService).checkNickname(any(CheckNicknameRequestDto.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/nickname/check")
                .content(checkNicknameJsonRequest)
                .cookie(new Cookie("registerToken", "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("사용할 수 있는 닉네임입니다."))

                .andDo(MockMvcRestDocumentationWrapper.document("nickname/check",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("User")
                                        .description("닉네임 중복 유무를 확인한다.")
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지")
                                        )
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
        Mockito.doNothing().when(userService).signupUser(anyString(), any(SignUpRequestDto.class));
        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/signup")
                .cookie(new Cookie("registerToken", "test"))
                .content(signUpRequestDto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                .andDo(MockMvcRestDocumentationWrapper.document("signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("User")
                                        .description("온보딩 정보를 토대로 회원가입을 진행한다.")
                                        .requestFields(
                                                fieldWithPath("nickname").description("유저 닉네임")
                                        )
                                        .responseFields(
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지")
                                        )
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("registerToken").description("온보딩을 위한 레지스터토큰")
                        )
                ));
    }

    @Test
    @DisplayName("유저정보를 조회한다.")
    public void getUserInfo() throws Exception {

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto("유저 닉네임", "유저 이메일");

        Mockito.when(userService.getUserInfo(anyString()))
                .thenReturn(userInfoResponseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/user-info")
                .cookie(new Cookie("accessToken", "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.code").value(200))
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
                                                fieldWithPath("isSuccess").description("성공 여부"),
                                                fieldWithPath("code").description("응답 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
                                                fieldWithPath("payload").description("응답 데이터"),
                                                fieldWithPath("payload.nickname").description("유저의 닉네임"),
                                                fieldWithPath("payload.email").description("유저의 이메일")
                                        )
                                        .build()
                        ),
                        requestCookies(
                                cookieWithName("accessToken").description("JWT Token")
                        )
                ));
    }
}
