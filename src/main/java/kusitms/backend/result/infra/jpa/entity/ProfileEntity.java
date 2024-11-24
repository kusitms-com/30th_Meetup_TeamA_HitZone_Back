package kusitms.backend.result.infra.jpa.entity;

import jakarta.persistence.*;
import kusitms.backend.global.domain.BaseTimeEntity;
import kusitms.backend.result.infra.converter.StringListConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recommend_profile")
public class ProfileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private ResultEntity resultEntity;


    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String type;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> hashTags;

    public ProfileEntity(
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

    public static ProfileEntity toEntity(
            Long id,
            String imgUrl,
            String nickname,
            String type,
            List<String> hashTags
    ) {
        return new ProfileEntity(id, imgUrl, nickname, type, hashTags);
    }

    public void assignToResultEntity(ResultEntity resultEntity) {
        this.resultEntity = resultEntity;
    }
}
