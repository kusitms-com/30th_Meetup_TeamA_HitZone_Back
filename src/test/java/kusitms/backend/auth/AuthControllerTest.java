package kusitms.backend.auth;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kusitms.backend.auth.application.AuthService;
import kusitms.backend.auth.presentation.AuthController;
import kusitms.backend.configuration.ControllerTestConfig;
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

@WebMvcTest(AuthController.class)
public class AuthControllerTest extends ControllerTestConfig {

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("토큰 재발급을 한다.")
    public void reIssueToken() throws Exception {

        Mockito.doNothing().when(authService).reIssueToken(anyString(), any(HttpServletResponse.class));

        // when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/token/re-issue")
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
                                        .tag("Auth")
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
