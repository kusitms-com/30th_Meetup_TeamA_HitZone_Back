package kusitms.backend.result.domain.enums;

import kusitms.backend.result.domain.value.Reference;
import kusitms.backend.result.domain.value.ReferencesGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum JamsilStadiumStatusType implements StadiumStatusType {

    RED("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/red.svg",
            "레드석",
            "#DC032A",
            "해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이예요.",
            List.of("응원도 적당히 즐길 수 있지만, 야구나 함께 온 동행자와의 대화에도 집중할 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 다양한 것들을 모두 적절히 즐길 수 있는 구역이예요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "시야가 중요하신 분",
                                            new String[]{"외야와 가까운 쪽은 예매 시 시야 확인이 필요해요. 기둥이나 그물망으로 시야 방해를 받을 수 있어요!"}
                                    ),
                                    new Reference(
                                            "시끄러운 것을 좋아하지 않는 분",
                                            new String[]{"오렌지석이 앞에 있어서 스피커 때문에 많이 시끄러워요. 조용한 관람을 원하시면 다른 구역을 추천해요!"}
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of(),
            List.of(),
            List.of("선수들 가까이", "경기장 한눈에 보기"),
            new String[]{"[1루] 2-3 Gate [3루] 2-1 Gate"},
            new String[]{
                    "[1루] 약 24~30cm(10n열), 약 33~38cm(20n열),",
                    "[3루]  약 24~30cm(1nn열), 약 33~38cm(22n열)"
            },
            new String[]{"[1루] 약 25cm [3루] 약 25cm"},
            new String[]{""}
    ),
    BLUE("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/blue.svg",
            "블루석",
            "#4699F2",
            "힘차게 응원도 가능하고, 야구에 집중도 할 수 있는 구역이에요!",
            List.of("힘차게 응원도 가능하고, 야구에 집중도 할 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 비교적 조용히 경기 관람이 가능한 구역이예요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "시야가 중요하신 분",
                                            new String[]{"홈과 가까운 쪽은 예매 시 시야 확인이 필요해요. 그물망으로 인해 시야 방해를 받을 수 있어요!"}
                                    ),
                                    new Reference(
                                            "적당한 응원을 하고 싶으신 분",
                                            new String[]{"비교적 조용히 경기를 관람하고 적당히 응원할 수 있는 곳이에요. 열정적인 응원을 하고 싶으신 분들은 다른 구역을 더 추천해요!"}
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("빠른 퇴장 가능", "편리한 화장실 이용", "선수들 가까이"),
            List.of(),
            List.of("열정적인 응원"),
            new String[]{"[1루] 2-3 Gate [3루] 2-1 Gate [중앙문] 1-1 Gate"},
            new String[]{"[1루 & 3루] 약 27~33cm"},
            new String[]{"[1루 & 3루] 약 30~38cm"},
            new String[]{""}
    ),
    NAVY("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/navy.svg",
            "네이비석",
            "#242953",
            "높은 곳에서 야구를 전체적으로 볼 수 있는 구역이에요!",
            List.of("높은 곳에서 야구를 전체적으로 볼 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 높은 층수에 위치해 있어요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "높은 곳을 안 좋아하시는 분",
                                            new String[]{
                                                    "높은 시야에서 야구를 관람할 수 있기 때문에",
                                                    "홈과 가까운 시야에서 관람을 원하시면",
                                                    "다른 구역을 추천해요!"}
                                    ),
                                    new Reference(
                                            "높은 곳까지 걸어 오르기 힘드신 분",
                                            new String[]{
                                                    "높은 층수에 위치해 있기 때문에",
                                                    "좌석을 찾아가기까지 걸어오르는 과정이 필요해요.",
                                                    "무릎이나 체력이 안 좋으시면 비추천해요!"}
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("경기장 한눈에 보기"),
            List.of("햇빛 싫어요", "비 맞기 싫어요"),
            List.of("열정적인 응원", "선수들 가까이"),
            new String[]{"[1루] 2-3 Gate [3루] 2-1 Gate [중앙문] 1-1 Gate"},
            new String[]{"약 53cm(2~20열), 92cm(10열, 11열 일부)"},
            new String[]{"약 24cm(2~20열), 123cm(10열, 11열 일부)"},
            new String[]{""}
    ),
    ORANGE("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/orange.svg",
            "오렌지석",
            "#E16900",
            "야구장의 응원이 가장 열정적인 응원석 구역이에요!",
            List.of("야구장의 응원이 가장 열정적인 응원석 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 열정적인 응원이 이루어져요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "시끄러운 것을 좋아하지 않는 분",
                                            new String[]{
                                                    "응원단상이 앞에 위치해 있어요.",
                                                    "치어리더 공연도 이루어지며,",
                                                    "관중들도 열정적인 응원을 합니다!",
                                                    "해당 구역 앞에 응원을 위한 앰프가 설치되어 있어요.",
                                                    "조용한 관람을 원하시면 다른 구역을 추천해요!"}
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("열정적인 응원", "선수들 가까이"),
            List.of(),
            List.of("다른 팀 팬과", "큰 소리 싫어요", "경기장 한눈에 보기"),
            new String[]{"[1루] 2-3 Gate [3루] 2-1 Gate"},
            new String[]{"[1루 & 3루] 약 33~39cm"},
            new String[]{"[1루 & 3루] 약 30cm"},
            new String[]{""}
    ),
    EXITING("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/exciting.svg",
            "익사이팅석",
            "#FFE23E",
            "야구 필드 안에 들어와 있는 느낌을 받을 수 있는 구역이에요!",
            List.of("야구 필드 안에 들어와 있는 느낌을 받을 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 연령제한이 있어요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "연령제한에 적용되는 분",
                                            new String[]{
                                                    "파울볼이 튀어 들어올 수 있는",
                                                    "위험 때문에 13세 이상만 입장할 수 있어요."}
                                    ),
                                    new Reference(
                                            "야구 관람에서 시야가 중요하신 분",
                                            new String[]{
                                                    "경기장과 매우 가까운 곳에",
                                                    "위치하고 있기 때문에",
                                                    "오히려 시야가 좋지 않을 수 있어요."}
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("선수들 가까이", "열정적인 응원"),
            List.of(),
            List.of("다른 팀 팬과", "큰 소리 싫어요", "경기장 한눈에 보기"),
            new String[]{"내야게이트로 입장 후 헬멧 대여 필수(착용은 선택!)"},
            new String[]{"약 40cm(1열), 19cm(2~3열)"},
            new String[]{"약 72cm(1열), 63cm(2~3열)"},
            new String[]{""}
    ),
    GREEN("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/green.svg",
            "외야그린석",
            "#339600",
            "사람들이 몰리지 않아 비교적 한적하게 경기를 즐길 수 있는 구역이에요!",
            List.of("사람들이 몰리지 않아 비교적 한적하게 경기를 즐길 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 시야 제한석이 있어요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "시야가 중요하신 분",
                                            new String[]{"시야 제한석이 존재하며, 위치 특성상 야구 경기 흐름을 한눈에 파악하기 어려워요."}
                                    ),
                                    new Reference(
                                            "그린석 입장 입구",
                                            new String[]{"1,3루 출입구의 반대쪽에 있는 외야 출입구로만 입장이 가능해요. 입장에 참고하세요!"}
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과", "다른 팀 팬과"),
            List.of("경기장 한눈에 보기"),
            List.of(),
            List.of("열정적인 응원", "선수들 가까이", "빠른 퇴장 가능", "편리한 화장실 이용", "햇빛 싫어요", "비 맞기 싫어요"),
            new String[]{
                    "[외야 게이트 이용(내야 불가)]",
                    "[1루] 1-4 Gate [3루] 1-3 Gate"
            },
            new String[]{""},
            new String[]{""},
            new String[]{
                    "잠실야구장의 외야석은 굉장히 커서 높고 가팔라요!",
                    "음식을 구매하셨거나, 짐이 많으시다면",
                    "좌석에 앉기 위해 올라가실 때 꼭 유의하세요!"
            }
    ),
    TABLE("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/table.svg",
            "테이블석",
            "#7C0065",
            "다양한 음식을 골라 테이블에 펼쳐놓고 먹을 수 있는 구역이에요!",
            List.of("다양한 음식을 골라 테이블에 펼쳐놓고 먹을 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 예매 시 위치 확인이 필요해요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "3루 테이블석",
                                            new String[]{
                                                    "응원단상과는 다소 거리가 있어",
                                                    "응원을 원하시는 분들께는 다소 아쉬울 수 있어요.",
                                                    "또한 햇빛이 뜨거울 수 있으니 참고하세요!",
                                                    "특히 3루 테이블석 112블록은 앞에는 공을 막는 그물, 옆에는 기둥이 있어서 시야 방해를 받을 수 있어요!"
                                            }
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("음식 먹기 편한", "빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("다른 팀 팬과", "경기장 한눈에 보기"),
            new String[]{"[1루] 2-3 Gate [3루] 2-1 Gate"},
            new String[]{"약 95cm(1열), 56cm(2~4열)"},
            new String[]{"약 56cm"},
            new String[]{""}
    ),
    PREMIUM("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/premium.svg",
            "프리미엄석",
            "#185DDD",
            "테이블에서 맛있는 음식을 먹으면서 선수들을 가장 가까이서 볼 수 있는 구역이에요!",
            List.of("테이블에서 맛있는 음식을 먹으면서 선수들을 가장 가까이서 볼 수 있는 구역이에요!"),
            "두산베어스, LG 트윈스",
            "상대팀",
            "해당 구역은 티켓 확인을 2번 해요.",
            List.of(
                    new ReferencesGroup(
                            JamsilStadiumStatusType.DEFAULT_TITLE,
                            List.of(
                                    new Reference(
                                            "두 번의 티켓 확인",
                                            new String[]{
                                                    "입구에서 티켓 보여주면 1차 통과",
                                                    "입장 후 직원이 2차로 티켓을 검사하고 자리 안내를 해줄 거예요!"
                                            }
                                    ),
                                    new Reference("입장 후 팔찌",
                                            new String[]{
                                                    "프리미엄석은 입장 후 팔찌를 받아요.",
                                                    "내야 쪽에서 이동하는 경우에는",
                                                    "이 팔찌만 보여주면 자유롭게 재출입할 수 있어요!"
                                            }
                                    )
                            )
                    )
            ),
            List.of("나 혼자", "같은 팀 팬과"),
            List.of("음식 먹기 편한", "선수들 가까이", "열정적인 응원", "빠른 퇴장 가능", "편리한 화장실 이용"),
            List.of(),
            List.of("다른 팀 팬과", "큰 소리 싫어요", "경기장 한눈에 보기"),
            new String[]{"[중앙게이트] 1-1 Gate"},
            new String[]{"약 40cm(1열), 55cm(2~6열)"},
            new String[]{"약 55cm"},
            new String[]{""}
    ),
    ;

    private static final String DEFAULT_TITLE = "참고하세요";
    private final String imgUrl;
    private final String zoneName;
    private final String zoneColor;
    private final String oneLineDescription;
    private final List<String> explanations;
    private final String firstBaseSide;
    private final String thirdBaseSide;
    private final String tip;
    private final List<ReferencesGroup> referencesGroup;
    private final List<String> page1Keywords;
    private final List<String> page2Keywords;
    private final List<String> page3Keywords;
    private final List<String> forbiddenKeywords;
    private final String[] entrance;
    private final String[] stepSpacing;
    private final String[] seatSpacing;
    private final String[] usageInformation;
}