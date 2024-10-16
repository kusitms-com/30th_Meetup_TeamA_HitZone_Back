package kusitms.backend.stadium.domain.enums;

import kusitms.backend.stadium.common.Reference;
import kusitms.backend.stadium.common.ReferencesGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum KtWizStadiumStatusType implements StadiumStatusType{

    CHEERING("응원지정석",
            List.of("응원 단상과 가까워, 야구를 열정적으로 응원할 수 있는 분위기의 구역이에요!"),
            "해당 구역은 예매 시 응원단상 위치 확인이 필요해요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "응원단상 위치 고려",
                                            """
                                                    1루 ‘홈팀’ KT 위즈의 응원단상은
                                                    109, 110 구역에 위치해요.
                                                    3루 ‘원정팀’ 원정 응원단상은
                                                    121, 122 구역에 위치해요.
                                                    """
                                    ),
                                    new Reference(
                                            "시끄러운 것을 좋아하지 않는 분",
                                            """
                                                    해당 구역 앞에는 응원을 위한 앰프가 설치되어 있어요.
                                                    조용한 관람을 원하시면 다른 구역을 추천해요!
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("열정적인 응원"),
            List.of(),
            List.of("다른 팀 팬과", "큰 소리 싫어요", "음식 먹기 편한", "삼겹살 구워먹기 가능")

    ),
    GENIETV("지니TV석",
            List.of("테이블 석이라 음식 취식이 편리한 구역이에요!"),
            "해당 구역은 3루 테이블석이라 편하게 관람을 할 수 있어요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "전용 게이트, 전용 팔찌",
                                            """
                                            3루 1게이트로 입장 후, 전용 게이트가 있어요.
                                            전용 팔찌를 채워주셔서, 재입장 시 편리해요.
                                            """
                                    ),
                                    new Reference(
                                            "넓은 좌석 간격",
                                            "앞,뒤,옆 간격이 넓어서 관람 시 편해요!"
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("음식 먹기 편한"),
            List.of(),
            List.of("삼겹살 구워먹기 가능")
    ),
    CENTER("중앙지정석",
            List.of(
                    "특정 팀에 구애받지 않고 응원하는 분위기의 구역이에요!",
                    "스카이존보다 훨씬 가깝게 볼 수 있어요."
            ),
            "해당 구역은 특정 팀과 무관하게 응원할  수 있는 분위기예요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "타 팀 팬들과 함께",
                                            """
                                                    동행자가 타 팀일 때 가기 좋아요.
                                                    어느 한 팀에 치우쳐져서 응원하는 분위기가 아니예요!
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("열정적인 응원"),
            List.of(),
            List.of("삼겹살 구워먹기 가능", "큰 소리 싫어요")
    ),
    YBOX("Y박스석",
            List.of("선수들을 가까이서 볼 수 있는 구역이에요!"),
            "해당 구역은 1루 테이블석이라 편리하게 음식을 먹을 수 있어요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "편리한 음식 섭취",
                                            "테이블 석이라, 편리하게 음식을 먹을 수 있어요."
                                    ),
                                    new Reference(
                                            "전용 게이트, 팔찌",
                                            """
                                                    Y박스석은 전용 게이트를 이용할 수 있어요!
                                                    전용 팔찌를 채워주셔서, 재입장 시 편리해요.
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("음식 먹기 편한"),
            List.of(),
            List.of("삼겹살 구워먹기 가능")

    ),
    SKYBOX("스카이박스(4층)",
            List.of(
                    "회의실처럼 생긴 내부에서 쾌적하게 야구를 볼 수 있는 구역이에요!",
                    "(빔프로젝터, 에어컨까지 구비되어 있어요)"
            ),
            "해당 구역은 별도의 룸에서 편리하게 관람할 수 있어요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "별도의 티켓 부스",
                                            """
                                                    예약 정보를 확인한 뒤 티켓을 수령하시고,
                                                    전용 엘레베이터를 타고 올라갈 수 있어요.
                                                    """
                                    ),
                                    new Reference(
                                            "배달 주문시 꿀팁",
                                            """
                                                    주소를 수원KT위즈파크로 설정하신 후
                                                    상세 주소에 ‘n번 엘레베이터 앞’으로 기재하시면
                                                    편하게 음식을 픽업하실 수 있어요!
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("빠른 퇴장 가능", "편리한 화장실 이용", "음식 먹기 편한", "경기장 한 눈에 보기"),
            List.of("햇빛 싫어요", "비 맞기 싫어요"),
            List.of("선수들 가까이", "삼겹살 구워먹기 가능", "높은 곳 싫어요")
    ),
    SKYZONE("스카이존(5층)",
            List.of("높은 곳에서 경기를 한 눈에 볼 수 있는 구역이에요!"),
            "해당 구역은 높은 곳에 위치해있어요!",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "높은 곳을 안 좋아하시는 분",
                                            """
                                                    높은 시야에서 야구를 관람할 수 있기 때문에,
                                                    홈과 가까운 시야에서 관람을 원하시면
                                                    다른 구역을 추천해요!
                                                    """
                                    ),
                                    new Reference(
                                            "높은 곳까지 걸어 오르기 힘드신 분",
                                            """
                                                    높은 층수에 위치해 있기 때문에
                                                    좌석을 찾아가기까지 걸어오르는 과정이 필요해요.
                                                    무릎이나 체력이 안 좋으시면 비추천해요!
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("경기장 한 눈에 보기"),
            List.of(),
            List.of("선수들 가까이", "높은 곳 싫어요", "삼겹살 구워먹기 가능")
    ),
    EXITING("하이파이브존/익사이팅석",
            List.of(
                    "(1루 하이파이브존) KT가 경기 승리 시, 선수들과 하이파이브 할 수 있는 구역이에요! 전반적으로 앉아서 응원하는 분위기며, 선수들을 굉장히 가까이에서 볼 수 있는 구역이에요.",
                    "(3루 익사이팅석) 선수들을 굉장히 가까이에서 볼 수 있는 구역이에요."
                    ),
            "",
            List.of(
                    new ReferencesGroup(
                            "하이파이브존(1루) 참고하세요",
                            List.of(
                                    new Reference(
                                            "승리 시 선수와 하이파이브",
                                            """
                                                    미리 중앙 빅또리센터로 가서 번호표를 수령해야,
                                                    경기 승리 시 선수들과 하이파이브를 할 수 있어요.
                                                    """
                                    ),
                                    new Reference(
                                            "전용 팔찌",
                                            """
                                                    1루 하이파이브존 입장 전,
                                                    한 번 더 티켓 검사를 진행한 후 ‘입장 팔찌’를 채워주세요.
                                                    이 팔찌가 있어야 재입장을 할 수 있으니 유의하세요!
                                                    """
                                    ),
                                    new Reference(
                                            "불펜 투수 연습 관람",
                                            """
                                                    ‘오른쪽’으로 예매를 하시면 불펜 투수들이
                                                    연습하는 모습을 가깝게 보실 수 있어요.
                                                    """
                                    )
                            )
                    ),
                    new ReferencesGroup(
                            "익사이팅석 (3루) 참고하세요",
                            List.of(
                                    new Reference(
                                            "전용 팔찌",
                                            """
                                                    3루 하이파이브존 입장 전,
                                                    한 번 더 티켓 검사를 진행한 후 ‘입장 팔찌’를 채워주세요.
                                                    이 팔찌가 있어야 재입장을 할 수 있으니 유의하세요!
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("선수들 가까이", "열정적인 응원"),
            List.of(),
            List.of("음식 먹기 편한", "경기장 한 눈에 보기", "삼겹살 구워먹기 가능", "큰 소리 싫어요")
    ),
    SHOPPING("KT알파쇼핑석",
            List.of("중앙테이블석이라, 음식 섭취하기 편한 구역이에요!"),
            "해당 구역은 중앙 테이블석이고, 빨리 예매하는 것이 좋아요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "빠른 매진",
                                            """
                                                    매진이 빠른 편이라 가고 싶다면
                                                    예매 일자가 열리는 날을 체크해둔 후
                                                    예매하는 것이 좋아요!
                                                    """


                                    ),
                                    new Reference(
                                            "가까운 게이트",
                                            "중앙게이트 6~7 입구로 들어가면 가장 가까워요."
                                    ),
                                    new Reference(
                                            "엄청난 시야",
                                            "수원KT위즈파크에서 손꼽힐 만큼 시야가 정말 좋아요."
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("음식 먹기 편한"),
            List.of(),
            List.of("삼겹살 구워먹기 가능")
    ),
    GENIE("지니존",
            List.of("포수 바로 뒤에서 관람할 수 있는 구역이에요!"),
            "해당 구역은 경기를 생동감있게 볼 수 있어요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "포수 뒤 관람 공간",
                                            """
                                                    구단마다 포수 뒤 관람 공간을 부르는 용어가 상이한데,
                                                    위즈 파크의 경우 ‘지니존’이에요.
                                                    좌/중/우 세 부분으로 나뉘고
                                                    각각 5열로 구성되어 있어요.
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("선수들 가까이"),
            List.of(),
            List.of("경기장 한 눈에 보기", "삼겹살 구워먹기 가능", "큰 소리 싫어요")
    ),
    TVING("티빙 테이블석",
            List.of("외야 테이블석이며, 2인 테이블 3개로 구성되어있는 구역이에요!"),
            "해당 구역은 외야 테이블석이에요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "외야에 위치",
                                            "외야에 있는 테이블석이에요."
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("음식 먹기 편한", "경기장 한 눈에 보기"),
            List.of(),
            List.of("삼겹살 구워먹기 가능", "선수들 가까이", "높은 곳 싫어요")
    ),
    GRASS("외야 잔디 자유석",
            List.of(
                    "넓은 외야에 잔디가 깔려 있어 앉아서 관람할 수 있는 구역이에요!",
                    "계단처럼 층이 나뉘어져 있어요."
                    ),
            "해당 구역은 외야의 잔디로 이뤄진 구역으로 피크닉 기분을 낼 수 있어요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "관중이 몰릴 땐 일찍",
                                            """
                                                    관중이 몰리는 날에는,
                                                    야구장에 일찍 가서 자리를 잡는 것이 좋아요.
                                                    ‘2시간 전’부터 입장 가능해요!
                                                    """
                                    ),
                                    new Reference("돗자리 필수",
                                            """
                                                    다른 구역처럼 좌석이 있는 것이 아니라,
                                                    돗자리나 미니 테이블을 가져오면
                                                    편리하게 관람하실 수 있어요!
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("음식 먹기 편한", "경기장 한 눈에 보기"),
            List.of(),
            List.of("선수들 가까이", "삼겹살 구워먹기 가능")
    ),
    CAMPING("키즈랜드 캠핑존",
            List.of("텐트에서 야구를 볼 수 있는 구역이에요! 고기를 구워 먹는 것이 가능해요."),
            "해당 구역은 텐트로 구성되어 있으며 고기와 함께 즐길 수 있어요.",
            List.of(
                    new ReferencesGroup(
                            KtWizStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "고기와 함께",
                                            """
                                                    텐트 앞 테이블에서 간단하게 고기를
                                                    구워먹을 수 있는 구역이에요!
                                                    신분증을 가지고 3층
                                                    ‘키즈랜드 캠핑 운영사무국’으로 가시면 캠핑용품을
                                                    무료로 대여할 수 있어요!
                                                    5층 캠핑존에 전자레인지와 셀프 라면기도 있어요
                                                    """
                                    ),
                                    new Reference(
                                            "아이들과 함께",
                                            """
                                                    아이들이 있어야 입장 가능한 구역이예요.
                                                    (최대 4인까지 입장 가능해요)
                                                    """
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("삼겹살 구워먹기 가능", "음식 먹기 편한", "빠른 퇴장 가능", "편리한 화장실 이용", "경기장 한 눈에 보기"),
            List.of("비 맞기 싫어요"),
            List.of()
    );

    private static final String DEFAULT_TITLE = "참고하세요";
    private final String zone;
    private final List<String> explanations;
    private final String tip;
    private final List<ReferencesGroup> referencesGroup;
    private final List<String> page1Keywords;
    private final List<String> page2Keywords;
    private final List<String> page3Keywords;
    private final List<String> forbiddenKeywords;

}
