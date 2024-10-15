package kusitms.backend.stadium.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProfileStatusType {

    EATING("이러다 공까지 먹어버러",
            "야구가 참 맛있고 음식이 재밌어요",
            "야구장에서 먹는 재미까지 놓치지 않는 당신! 야구장을 두 배로 재밌게 즐기는군요?",
            List.of("먹으러왔는데야구도한다?", "그래서여기구장맛있는거뭐라고?"),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("음식 먹기 편한", "경기장 한 눈에 보기", "삼겹살 구워먹기 가능", "빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("선수들 가까이", "열정적인 응원")
    ),
    LIFE("주6일 야구장 출퇴근러",
            "야구가 나의 삶이고, 야구가 나의 숨",
            "야구장에서 열정적인 응원을 보여주는 당신! 당신의 응원 덕에 선수들이 더 행복해 질 거예요!",
            List.of("월요일은심심해", "18시를공기로안다"),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("열정적인 응원", "선수들 가까이"),
            List.of(),
            List.of("다른 팀 팬과")
    ),
    ANGRY("마운드 직진러",
            "나와. 이럴 거면 내가 경기 뛸게",
            "야구를 통해 희노애락을 다 느끼는 당신! 몰입하며 보는 야구가 얼마나 재밌는 지 아시는군요?",
            List.of("오늘부터내가야구선수", "우리팀승리기원n일차"),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("열정적인 응원", "선수들 가까이"),
            List.of(),
            List.of("다른 팀 팬과")
    ),
    CALM("뜨거운 열기 속 침착러",
            "야구란 자고로 그라운드의 열기 속에서 고요함을 느끼는 것",
            "경기를 조용하게 관람하는걸 좋아하는 당신! 경기를 음미하는 것을 좋아하시는군요?",
            List.of("나는나의갈길을간다", "진짜는조용한법"),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("경기장 한 눈에 보기", "빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("열정적인 응원")
    ),
    TRAVEL("베이스 낭만 여행러",
            "야구장에서 한 페이지가 될 수 있게",
            "나의 소중한 직관 메이트와 추억을 쌓는 것이 좋은 당신! 야구장에서의 추억이 행복하길 바라요~",
            List.of("너와함께하는9이닝", "우리 다음에또갈까?"),
            List.of("같은 팀 팬과", "다른 팀 팬과"),
            List.of("빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("나 혼자")
    );


    private final String nickName;
    private final String type;
    private final String explanation;
    private final List<String> hastTags;
    private final List<String> page1Keywords;
    private final List<String> page2Keywords;
    private final List<String> page3Keywords;
    private final List<String> forbiddenKeywords;
}
