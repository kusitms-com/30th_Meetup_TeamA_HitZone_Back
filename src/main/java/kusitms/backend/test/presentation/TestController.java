package kusitms.backend.test.presentation;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.status.SuccessStatus;
import kusitms.backend.test.status.TestSuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<SuccessStatus>> healthCheck() {
        return ApiResponse.onSuccess(TestSuccessStatus._HEALTH_CHECK);
    }
}
