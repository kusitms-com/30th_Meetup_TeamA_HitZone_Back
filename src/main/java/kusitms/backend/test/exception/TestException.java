package kusitms.backend.test.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestException extends RuntimeException {
    private final TestErrorResult testErrorResult;

    @Override
    public String getMessage() {
        return testErrorResult.getMessage();
    }
}