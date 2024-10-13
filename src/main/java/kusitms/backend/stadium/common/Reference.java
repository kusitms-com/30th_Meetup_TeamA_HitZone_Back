package kusitms.backend.stadium.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Reference {
    private final String title;
    private final List<String> contents;
}