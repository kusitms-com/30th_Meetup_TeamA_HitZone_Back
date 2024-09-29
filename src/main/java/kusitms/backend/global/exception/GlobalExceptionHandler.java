package kusitms.backend.global.exception;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.dto.ErrorReasonDto;
import kusitms.backend.global.status.ErrorStatus;
import kusitms.backend.test.exception.TestErrorResult;
import kusitms.backend.test.exception.TestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Test 에러 처리
    @ExceptionHandler(TestException.class)
    public ResponseEntity<ApiResponse<ErrorReasonDto>> handleTestException(TestException e) {
        TestErrorResult errorResult = e.getTestErrorResult();
        return ApiResponse.onFailure(errorResult);
    }

    // AccessDeniedException 등 보안 관련 에러 처리
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorReasonDto> handleSecurityException(SecurityException e) {
        log.error("SecurityException: {}", e.getMessage());
        return ResponseEntity.status(ErrorStatus._UNAUTHORIZED.getHttpStatus())
                .body(ErrorStatus._UNAUTHORIZED.getReasonHttpStatus());
    }

    // 기타 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorReasonDto> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());

        if (e instanceof IllegalArgumentException) {
            return ResponseEntity.status(ErrorStatus._BAD_REQUEST.getHttpStatus())
                    .body(ErrorStatus._BAD_REQUEST.getReasonHttpStatus());
        }

        // 그 외 내부 서버 오류로 처리
        return ResponseEntity.status(ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorStatus._INTERNAL_SERVER_ERROR.getReasonHttpStatus());
    }
}