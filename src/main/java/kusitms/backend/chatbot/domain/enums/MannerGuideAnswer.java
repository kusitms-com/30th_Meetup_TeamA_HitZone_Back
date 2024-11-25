package kusitms.backend.chatbot.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MannerGuideAnswer implements GuideAnswer {

    Q1(1,
            null,
            """
                    🍔 음식물 섭취 후 뒷처리는 이렇게!
                    • 야구장에서 먹은 음식물은, 게이트에서 퇴장할 때 게이트 주변에 마련된 분리수거 규정에 맞춰 재활용해주세요!
                    • 야구장 내에서 먹거리를 구매한 경우, 다회용기에 음식을 받으셨을텐데 다회용기는 별도의 다회용기 수거함에 넣으면 됩니다!
                    
                    😖 모두가 불편하지 않게 관람해요!
                    • 자유로운 분위기여도 타인이 불편할 행동과 말은 하지 않는 것이 좋아요!• 좌석 마다 특징이 있어요! 예를 들어, ‘응원지정석’의 경우 해당 팀의 팬들이 열정적으로 응원하러 오는 공간이기 때문에, 다른 팀을 응원하는 건 지양해주세요.• 자리를 이탈하거나 돌아올 때는 경기 중간에 이동하는 것이 좋아요! 경기 도중 자리에서 일어나면 뒤쪽 관중들의 시야를 방해할 수 있기 때문이에요!• 야구장에서는 응원 도구(풍선, 클래퍼, 깃발 등)를 사용하는 것이 흔하지만, 크거나 소리가 큰 도구는 주위 사람들을 방해할 수 있으므로 적절하게 사용해야 해요.
                    
                    ⚾️ 파울볼을 주의하세요!
                    • 야구장에서는 파울볼이 관중석으로 날아오는 일이 자주 발생해요! 경기 중에는 항상 경기에 집중하고, 파울볼 경고 방송에 귀를 기울여야 해요!
                    • 위험한 상황이 생기면 스태프나 주변 관중이 주의를 환기할 수 있으니 경계를 늦추지 않는 것이 좋습니다.""",
            null)
    ;

    private final int id;
    private final String stadiumName;
    private final String answer;
    private final String imgUrl;
}
