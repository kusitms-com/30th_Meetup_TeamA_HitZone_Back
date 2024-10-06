package kusitms.backend.global.exception;

import kusitms.backend.global.dto.ApiResponse;
import kusitms.backend.global.dto.ErrorReasonDto;
import kusitms.backend.global.status.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<ErrorReasonDto>> handleCustomException(CustomException e) {
        log.error("CustomException occurred: {}", e.getMessage());
        return ApiResponse.onFailure(e.getErrorCode());
    }

    // Security 인증 관련 처리
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponse<ErrorReasonDto>> handleSecurityException(SecurityException e) {
        log.error("SecurityException: {}", e.getMessage());
        return ApiResponse.onFailure(ErrorStatus._UNAUTHORIZED);
    }

    // 기타 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorReasonDto>> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());

        if (e instanceof IllegalArgumentException) {
            return ApiResponse.onFailure(ErrorStatus._BAD_REQUEST);
        }
        // 그 외 내부 서버 오류로 처리
        return ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}