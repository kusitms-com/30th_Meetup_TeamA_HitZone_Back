package kusitms.backend.result.application;

import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.result.common.RecommendTopRankedZones;
import kusitms.backend.result.common.RecommendUserProfile;
import kusitms.backend.result.domain.entity.Profile;
import kusitms.backend.result.domain.entity.Result;
import kusitms.backend.result.domain.entity.Zone;
import kusitms.backend.result.domain.enums.JamsilStadiumStatusType;
import kusitms.backend.result.domain.enums.KtWizStadiumStatusType;
import kusitms.backend.result.domain.enums.ProfileStatusType;
import kusitms.backend.result.domain.enums.StadiumStatusType;
import kusitms.backend.result.domain.repository.ResultRepository;
import kusitms.backend.result.application.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.application.dto.response.GetProfileResponseDto;
import kusitms.backend.result.application.dto.response.GetZonesResponseDto;
import kusitms.backend.result.application.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.status.ResultErrorStatus;
import kusitms.backend.stadium.application.StadiumService;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import kusitms.backend.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultApplicationService {

    private final UserService userService;
    private final StadiumService stadiumService;
    private final ResultRepository resultRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public <T extends Enum<T> & StadiumStatusType> SaveTopRankedZoneResponseDto saveRecommendedZones(String accessToken, SaveTopRankedZoneRequestDto request) {

        T[] zones = switch (request.stadium()) {
            case "잠실종합운동장 (잠실)" -> (T[]) JamsilStadiumStatusType.values();
            case "수원KT위즈파크" -> (T[]) KtWizStadiumStatusType.values();
            default -> throw new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM);
        };

        ProfileStatusType recommendedProfile = RecommendUserProfile.getRecommendedUserProfile(
                ProfileStatusType.values(), List.of(request.clientKeywords()));
        List<T> recommendedZones = RecommendTopRankedZones.getTopRankedZones(
                zones, List.of(request.clientKeywords()));

        Long stadiumId = stadiumService.getIdByStadiumName(request.stadium());
        Result result;
        if (accessToken == null) {
            result = Result.builder()
                    .stadiumId(stadiumId)
                    .preference(request.preference())
                    .build();
            resultRepository.save(result);
        }
        else{
            Long userId = jwtUtil.getUserIdFromToken(accessToken);
            userService.isExistUserById(userId);

            result = Result.builder()
                    .userId(userId)
                    .stadiumId(stadiumId)
                    .preference(request.preference())
                    .build();
            resultRepository.save(result);
        }

        Profile profile = Profile.builder()
                .imgUrl(recommendedProfile.getImgUrl())
                .nickname(recommendedProfile.getNickName())
                .type(recommendedProfile.getType())
                .hashTags(recommendedProfile.getHashTags())
                .build();
        result.addProfile(profile);

       recommendedZones.forEach(zoneEnum -> {
           Zone zone = Zone.builder()
                   .name(zoneEnum.getZoneName())
                   .color(zoneEnum.getZoneColor())
                   .explanations(zoneEnum.getExplanations())
                   .tip(zoneEnum.getTip())
                   .referencesGroup(zoneEnum.getReferencesGroup())
                   .build();
           result.addZone(zone);
       });

       return SaveTopRankedZoneResponseDto.of(result.getId());
    }

    @Transactional(readOnly = true)
    public GetProfileResponseDto getRecommendedProfile(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ResultErrorStatus._NOT_FOUND_RESULT));
        return GetProfileResponseDto.from(result.getProfile());
    }

    @Transactional(readOnly = true)
    public GetZonesResponseDto getRecommendedZones(Long resultId, Long count) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ResultErrorStatus._NOT_FOUND_RESULT));
        List<GetZonesResponseDto.ZoneResponseDto> zones = result.getZones()
                .stream()
                .limit(count)
                .map(GetZonesResponseDto.ZoneResponseDto::from)
                .toList();
        return GetZonesResponseDto.of(zones);
    }
}
