package kusitms.backend.result.domain.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Reference {

    private final String title;
    private final String[] contents;

    @JsonCreator
    public Reference(
            @JsonProperty("title") String title,
            @JsonProperty("contents") String[] contents
    ) {
        this.title = title;
        this.contents = contents;
    }
}
