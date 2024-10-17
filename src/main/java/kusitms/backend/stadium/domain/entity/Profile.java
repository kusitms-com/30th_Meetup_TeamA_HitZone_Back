package kusitms.backend.stadium.domain.entity;

import jakarta.persistence.*;
import kusitms.backend.stadium.domain.converter.StringListConverter;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String explanation;

    @Lob
    @Convert(converter = StringListConverter.class)
    private List<String> hashTags;
}
