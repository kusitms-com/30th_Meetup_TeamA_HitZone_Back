package kusitms.backend.result.domain.model;

import kusitms.backend.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseTimeEntity {

    private Long id;
    private Result result;
    private String imgUrl;
    private String nickname;
    private String type;
    private List<String> hashTags;

    public Profile(
            Long id,
            String imgUrl,
            String nickname,
            String type,
            List<String> hashTags
    ) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.type = type;
        this.hashTags = hashTags;
    }

    public static Profile toDomain(
            Long id,
            String imgUrl,
            String nickname,
            String type,
            List<String> hashTags
    ) {
        return new Profile(id, imgUrl, nickname, type, hashTags);
    }

    public void assignToResult(Result result) {
        this.result = result;
    }
}
