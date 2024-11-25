package kusitms.backend.stadium.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StadiumInfo {

    LG_HOME("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/guide_home_lg.svg",
            "두산베어스, LG 트윈스",
            "상대팀"
    ),
    KT_HOME("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/kt/guide_home_kt.svg",
            "KT WIZ",
            "상대팀"
    )
    ;

    private final String imgUrl;
    private final String firstBaseSide;
    private final String thirdBaseSide;
}
