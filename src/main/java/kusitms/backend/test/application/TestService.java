package kusitms.backend.test.application;

import kusitms.backend.test.dto.request.TestDocsRequestDto;
import kusitms.backend.test.dto.response.TestDocsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    public TestDocsResponseDto testDocs(String name, String keyword, TestDocsRequestDto request){
        return TestDocsResponseDto.builder().keyword(keyword).tip(request.tip()).build();
    }
}
