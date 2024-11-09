package kusitms.backend.stadium.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StadiumInfo {

    LG_HOME("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/lg/guide_home_lg.png",
            "서울의 자존심, LG 트윈스 / 미라클 두산, 두산 베어스"
    ),
    KT_HOME("https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/guide/kt/guide_home_kt.png",
            "한국 프로 야구의 10번째 심장 KT wiz"
    )
    ;

    private final String imgUrl;
    private final String introduction;

}