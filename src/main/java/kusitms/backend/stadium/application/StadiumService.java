package kusitms.backend.stadium.application;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.stadium.common.RecommendedUserProfile;
import kusitms.backend.stadium.common.RecommendedTopRankedZones;
import kusitms.backend.stadium.domain.entity.Zone;
import kusitms.backend.stadium.domain.entity.Result;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.stadium.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.stadium.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.stadium.domain.enums.ProfileStatusType;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import kusitms.backend.stadium.domain.repository.ResultRepository;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.stadium.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import kusitms.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final UserRepository userRepository;
    private final StadiumRepository stadiumRepository;
    private final ResultRepository resultRepository;

    @Transactional
    public <T extends Enum<T> & StadiumStatusType> SaveTopRankedZoneResponseDto recommendZones(SaveTopRankedZoneRequestDto request) {

        T[] zones = switch (request.stadium()) {
            case "잠실종합운동장" -> (T[]) JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> (T[]) KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._BAD_REQUEST_STADIUM);
        };

        ProfileStatusType recommendedProfile = RecommendedUserProfile.getRecommendedUserProfile(
                ProfileStatusType.values(), List.of(request.clientKeywords()));
        List<T> recommendZones = RecommendedTopRankedZones.getTopRankedZones(
                zones, List.of(request.clientKeywords()));

        Stadium stadium = stadiumRepository.findByName(request.stadium());
        Result result = Result.builder()
                .stadium(stadium)
                .preference(request.preference())
                .profile(recommendedProfile)
                .build();
        resultRepository.save(result);

       recommendZones.forEach(zoneEnum -> {
           Zone zone = Zone.builder()
                   .result(result)
                   .zone((StadiumStatusType) zoneEnum)
                   .build();
       });

       return SaveTopRankedZoneResponseDto.from(result.getId());

    }
}
