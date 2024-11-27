package kusitms.backend.result.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProfileStatusType {

    EATING("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/eating.webp",
            "이러다 공까지 먹어버러",
            "야구가 참 맛있고 음식이 재밌어요",
            List.of("#야구장미식가", "#먹으러왔는데야구도한다?"),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("음식 먹기 편한", "경기장 한 눈에 보기", "삼겹살 구워먹기 가능", "빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("선수들 가까이", "열정적인 응원")
    ),
    LIFE("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/life.webp",
            "주6일 야구장 출퇴근러",
            "야구가 나의 삶이고, 야구가 나의 숨",
            List.of("#비공식응원단장", "#오늘도야구장출석완료"),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("열정적인 응원", "선수들 가까이"),
            List.of(),
            List.of("다른 팀 팬과")
    ),
    ANGRY("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/angry.webp",
            "마운드 직진러",
            "나와. 이럴 거면 내가 경기 뛸게",
            List.of("#야구와인생동행", "#일희일비야구중독자"),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("열정적인 응원", "선수들 가까이"),
            List.of(),
            List.of("다른 팀 팬과")
    ),
    CALM("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/calm.webp",
            "뜨거운 열기 속 침착러",
            "야구란 자고로 그라운드의 열기 속에서 고요함을 느끼는 것",
            List.of("#야구는천천히음미하는것", "#디테일에빠지다"),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("경기장 한 눈에 보기", "빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("열정적인 응원")
    ),
    TRAVEL("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/recommendation/travel.webp",
            "베이스 낭만 여행러",
            "야구장에서 한 페이지가 될 수 있게",
            List.of("#너와함께하는9이닝", "#야구는추억을남긴다"),
            List.of("같은 팀 팬과", "다른 팀 팬과"),
            List.of("빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("나 혼자")
    );

    private final String imgUrl;
    private final String nickName;
    private final String type;
    private final List<String> hashTags;
    private final List<String> page1Keywords;
    private final List<String> page2Keywords;
    private final List<String> page3Keywords;
    private final List<String> forbiddenKeywords;
}
