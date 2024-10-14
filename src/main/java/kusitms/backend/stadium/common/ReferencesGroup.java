package kusitms.backend.stadium.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReferencesGroup {
    private final String title;
    private final List<Reference> references;
}