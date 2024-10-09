package kusitms.backend.test.presentation;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.test.status.TestErrorStatus;
import kusitms.backend.test.status.TestSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

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

}
