package kusitms.backend.result.domain.service;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.result.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.result.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.result.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResultDomainService {

    public <T extends Enum<T> & StadiumStatusType> T[] extractZonesByStadiumName (String stadiumName){
        T[] zones = switch (stadiumName) {
            case "잠실종합운동장 (잠실)" -> (T[]) JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> (T[]) KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };
        return zones;
    }



}
