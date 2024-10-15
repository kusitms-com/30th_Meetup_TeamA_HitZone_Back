package kusitms.backend.test.presentation;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.global.status.SuccessStatus;
import kusitms.backend.test.application.TestService;
import kusitms.backend.test.dto.request.TestDocsRequestDto;
import kusitms.backend.test.dto.response.TestDocsResponseDto;
import kusitms.backend.test.status.TestErrorStatus;
import kusitms.backend.test.status.TestSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

    private final TestService testService;

    // 헬스체크용 API
    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<Void>> healthCheck() {
        return ApiResponse.onSuccess(TestSuccessStatus._HEALTH_CHECK);
    }

    // 테스트 에러용 API
    @GetMapping("/test-error")
    public void getError() {
        throw new CustomException(TestErrorStatus._TEST_ERROR);
    }

    // rest-docs 테스트용 API
    @PostMapping("/test/docs")
    public ResponseEntity<ApiResponse<TestDocsResponseDto>> testDocs(@RequestParam String name,
                                                      @RequestParam String keyword,
                                                      @RequestBody TestDocsRequestDto request) {
        return ApiResponse.onSuccess(SuccessStatus._OK, testService.testDocs(name, keyword, request));

    }

}
