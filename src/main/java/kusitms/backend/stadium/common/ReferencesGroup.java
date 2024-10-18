package kusitms.backend.stadium.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ReferencesGroup {

    private String groupTitle;
    private List<Reference> references;

    @JsonCreator
    public ReferencesGroup(
            @JsonProperty("groupTitle") String groupTitle,
            @JsonProperty("references") List<Reference> references
    ) {
        this.groupTitle = groupTitle;
        this.references = references;
    }
}