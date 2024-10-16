package kusitms.backend.chatbot.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseballGuideAnswer implements GuideAnswer {

    Q1(1, null,
            new String[]{
                    "1. 경기 전 우천 취소\n" +
                            "• 경기 시작 전 비가 많이 와서 경기장 상태가 불량할 경우, 구단의 경기 운영 책임자가 심판과 협의하여 경기를 취소할 수 있어요.\n" +
                            "• 보통 경기 시작 3시간 전부터 경기장 상태를 체크하고, 경기 직전에 우천 취소 여부가 결정돼요.",

                    "2. 경기 중 우천 취소\n" +
                            "• 경기 도중 비가 내려 경기를 진행하기 어려울 경우, 심판이 경기 중지를 선언할 수 있어요.\n" +
                            "• KBO 규정에 따라 경기가 5회 말까지 진행되었을 때(또는 홈팀이 5회 초에 리드 중일 때), 경기 결과는 유효하며, 남은 경기는 치르지 않고 그 시점의 점수로 경기 결과가 확정돼요.\n" +
                            "• 5회 초나 그 이전에 경기가 중단될 경우, 경기는 노게임으로 처리되고 추후 재경기가 편성돼요.\n" +
                            "• 노게임이 선언된 경기는 그 시즌 안에 해당 경기가 다시 편성됩니다. 이때 경기는 처음부터 다시 진행돼요.\n",

                    "3. 우천에 의한 경기 연기\n" +
                            "• 비가 내리기 시작하더라도 심판이 경기를 일시 중단하고 기다리다가, 비가 그치거나 그라운드 상태가 회복되면 경기를 재개할 수도 있어요. 이때 경기가 30분 이상 지연될 수 있어요."
            }, null),

    Q2(2, null,
            new String[]{
                    "야구 경기의 시간은 평균 3시간입니다.",

                    "경기 시작 시간은 평일에는 오후 6시 30분, 주말이나 공휴일에는 상황에 따라 오후 2시와 오후 5시 시작으로 나뉘어져요!",

                    "야구에는 쉬는 시간과 같이 느껴지는 ‘클리닝 타임’이 있어요! 5번의 공격과 5번의 수비가 끝난 ‘5회 말’에 짧은 휴식 시간을 갖습니다.",

                    "선수들도 몸을 풀고, 경기장도 재정비 시간을 가집니다! 이 시간을 활용하여 먹거리를 사오거나, 화장실을 다녀오는 것을 추천 드려요!"
            }, null)
    ;

    private final int id;
    private final String stadiumName;
    private final String[] answers;
    private final String imgUrl;
}