package kusitms.backend.result.domain.model;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.infra.converter.StringListConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recommend_profile")
public class Profile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String type;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> hashTags;

    @Builder
    public Profile(String imgUrl, String nickname, String type, String explanation, List<String> hashTags) {
        this.imgUrl = imgUrl;
        this.nickname = nickname;
        this.type = type;
        this.hashTags = hashTags;
    }

    public void assignToResult(Result result) {
        this.result = result;
    }
}
