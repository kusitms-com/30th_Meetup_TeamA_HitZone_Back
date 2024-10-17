package kusitms.backend.test.dto.response;

import lombok.Builder;

@Builder
public record TestDocsResponseDto(String keyword, String tip) {
}
