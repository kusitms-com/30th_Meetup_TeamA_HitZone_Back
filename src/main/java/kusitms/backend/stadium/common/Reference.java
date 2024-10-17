package kusitms.backend.stadium.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Reference {

    private String title;
    private String content;

    @JsonCreator
    public Reference(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content
    ) {
        this.title = title;
        this.content = content;
    }
}