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
import kusitms.backend.result.domain.repository.ProfileRepository;
import kusitms.backend.result.domain.repository.ResultRepository;
import kusitms.backend.result.domain.repository.ZoneRepository;
import kusitms.backend.result.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.dto.response.GetProfileResponseDto;
import kusitms.backend.result.dto.response.GetZonesResponseDto;
import kusitms.backend.result.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.status.ResultErrorStatus;
import kusitms.backend.stadium.domain.entity.Stadium;
import kusitms.backend.stadium.domain.repository.StadiumRepository;
import kusitms.backend.stadium.status.StadiumErrorStatus;
import kusitms.backend.user.domain.entity.User;
import kusitms.backend.user.domain.repository.UserRepository;
import kusitms.backend.user.status.UserErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private final UserRepository userRepository;
    private final StadiumRepository stadiumRepository;
    private final ResultRepository resultRepository;
    private final ZoneRepository zoneRepository;
    private final ProfileRepository profileRepository;
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

        Stadium stadium = stadiumRepository.findByName(request.stadium())
                .orElseThrow(() -> new CustomException(StadiumErrorStatus._NOT_FOUND_STADIUM));
        Result result;
        if (accessToken == null) {
            result = Result.builder()
                    .stadium(stadium)
                    .preference(request.preference())
                    .build();
            resultRepository.save(result);
        }
        else{
            Long userId = jwtUtil.getUserIdFromToken(accessToken);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(UserErrorStatus._NOT_FOUND_USER));
            log.info("유저 정보 조회 성공");

            result = Result.builder()
                    .stadium(stadium)
                    .user(user)
                    .preference(request.preference())
                    .build();
            resultRepository.save(result);
        }

        Profile profile = Profile.builder()
                .result(result)
                .imgUrl(recommendedProfile.getImgUrl())
                .nickname(recommendedProfile.getNickName())
                .type(recommendedProfile.getType())
                .explanation(recommendedProfile.getExplanation())
                .hashTags(recommendedProfile.getHashTags())
                .build();
        profileRepository.save(profile);

       recommendedZones.forEach(zoneEnum -> {
           Zone zone = Zone.builder()
                   .result(result)
                   .name(zoneEnum.getZoneName())
                   .color(zoneEnum.getZoneColor())
                   .explanations(zoneEnum.getExplanations())
                   .tip(zoneEnum.getTip())
                   .referencesGroup(zoneEnum.getReferencesGroup())
                   .build();
           zoneRepository.save(zone);
       });

       return SaveTopRankedZoneResponseDto.of(result.getId());
    }

    @Transactional(readOnly = true)
    public GetProfileResponseDto getRecommendedProfile(Long resultId) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ResultErrorStatus._NOT_FOUND_RESULT));
        Profile profile = profileRepository.findByResult(result)
                .orElseThrow(() -> new CustomException(ResultErrorStatus._NOT_FOUND_PROFILE));
        return GetProfileResponseDto.from(profile);
    }

    @Transactional(readOnly = true)
    public GetZonesResponseDto getRecommendedZones(Long resultId, Long count) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ResultErrorStatus._NOT_FOUND_RESULT));
        List<GetZonesResponseDto.ZoneResponseDto> zones = zoneRepository.findAllByResult(result)
                .stream()
                .limit(count)
                .map(GetZonesResponseDto.ZoneResponseDto::from)
                .toList();
        return GetZonesResponseDto.of(zones);
    }
}
