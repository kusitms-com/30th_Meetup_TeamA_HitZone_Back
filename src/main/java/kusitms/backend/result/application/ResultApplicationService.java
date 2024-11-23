package kusitms.backend.result.application;

import kusitms.backend.auth.jwt.JWTUtil;
import kusitms.backend.global.exception.CustomException;
import kusitms.backend.result.domain.service.RecommendTopRankedZonesService;
import kusitms.backend.result.domain.service.RecommendUserProfileService;
import kusitms.backend.result.domain.service.ResultDomainService;
import kusitms.backend.result.domain.model.Profile;
import kusitms.backend.result.domain.model.Result;
import kusitms.backend.result.domain.model.Zone;
import kusitms.backend.result.domain.enums.ProfileStatusType;
import kusitms.backend.result.domain.enums.StadiumStatusType;
import kusitms.backend.result.domain.repository.ResultRepository;
import kusitms.backend.result.application.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.application.dto.response.GetProfileResponseDto;
import kusitms.backend.result.application.dto.response.GetZonesResponseDto;
import kusitms.backend.result.application.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.status.ResultErrorStatus;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.user.application.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultApplicationService {

    private final UserApplicationService userApplicationService;
    private final StadiumApplicationService stadiumApplicationService;
    private final ResultDomainService resultDomainService;
    private final RecommendUserProfileService recommendUserProfileService;
    private final RecommendTopRankedZonesService recommendTopRankedZonesService;
    private final ResultRepository resultRepository;
    private final JWTUtil jwtUtil;

    @Transactional
    public <T extends Enum<T> & StadiumStatusType> SaveTopRankedZoneResponseDto saveRecommendedResult(String accessToken, SaveTopRankedZoneRequestDto request) {

        T[] zones = resultDomainService.extractZonesByStadiumName(request.stadium());
        ProfileStatusType recommendedProfile = recommendUserProfileService.getRecommendedUserProfile(
                ProfileStatusType.values(), List.of(request.clientKeywords()));
        List<T> recommendedZones = recommendTopRankedZonesService.getTopRankedZones(
                zones, List.of(request.clientKeywords()));

        Long stadiumId = stadiumApplicationService.getIdByStadiumName(request.stadium());

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
            userApplicationService.isExistUserById(userId);

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
