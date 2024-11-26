package kusitms.backend.result.application;

import kusitms.backend.user.infra.jwt.JWTUtil;
import kusitms.backend.result.application.dto.request.SaveTopRankedZoneRequestDto;
import kusitms.backend.result.application.dto.response.GetProfileResponseDto;
import kusitms.backend.result.application.dto.response.GetZonesResponseDto;
import kusitms.backend.result.application.dto.response.SaveTopRankedZoneResponseDto;
import kusitms.backend.result.domain.enums.ProfileStatusType;
import kusitms.backend.result.domain.model.Result;
import kusitms.backend.result.domain.repository.ResultRepository;
import kusitms.backend.result.domain.service.RecommendTopRankedZonesManager;
import kusitms.backend.result.domain.service.RecommendUserProfileManager;
import kusitms.backend.result.domain.service.ResultDomainService;
import kusitms.backend.stadium.application.StadiumApplicationService;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
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
    private final RecommendUserProfileManager recommendUserProfileManager;
    private final RecommendTopRankedZonesManager recommendTopRankedZonesManager;
    private final ResultDomainService resultDomainService;
    private final ResultRepository resultRepository;
    private final JWTUtil jwtUtil;

    /**
     *  클라이언트측으로부터 받은 키워드를 통해 추천 결과를 생성해준다.
     * @param accessToken 쿠키로부터 받은 어세스 토큰
     * @param request 구장명, 선호구역(1루석 or 3루석), 선호 키워드 리스트
     * @return 추천 결과 id
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    @Transactional
    public <T extends Enum<T> & StadiumStatusType> SaveTopRankedZoneResponseDto saveRecommendedResult(String accessToken, SaveTopRankedZoneRequestDto request) {
        T[] zones = stadiumApplicationService.extractZonesByStadiumName(request.stadium());
        Long stadiumId = stadiumApplicationService.getIdByStadiumName(request.stadium());
        Long userId = accessToken != null ? jwtUtil.getUserIdFromToken(accessToken) : null;
        if (userId != null) {
            userApplicationService.validateUserExistsById(userId);
        }

        ProfileStatusType recommendedProfile = recommendUserProfileManager.getRecommendedUserProfile(
                ProfileStatusType.values(), List.of(request.clientKeywords()));
        List<T> recommendedZones = recommendTopRankedZonesManager.getTopRankedZones(
                zones, List.of(request.clientKeywords()));

        Result result = resultDomainService.buildResult(userId, stadiumId, request.preference(), recommendedProfile, recommendedZones);
        Long resultId = resultRepository.saveResult(result).getId();

        return SaveTopRankedZoneResponseDto.of(resultId);
    }

    /**
     * 해당 결과에 해당하는 프로필 정보를 반환한다.
     * @param resultId 결과의 고유 id
     * @return 프로필 정보 (프로필 id, 이미지 Url, 닉네임, 타입, 해시태그 리스트)
     */
    @Transactional(readOnly = true)
    public GetProfileResponseDto getRecommendedProfile(Long resultId) {
        Result result = resultRepository.findResultById(resultId);
        return GetProfileResponseDto.from(result.getProfile());
    }

    /**
     * 해당 결과에 해당하는 추천 구역 리스트를 반환한다.
     * @param resultId 결과의 고유 id
     * @param count 받고자 하는 추천 받은 구역의 개수 ()
     * @return 구역 리스트 (구역 id, 구역명, 구역 색상, 구역 설명 리스트, 구역 팁, 구역 참고사항 리스트)
     */
    @Transactional(readOnly = true)
    public GetZonesResponseDto getRecommendedZones(Long resultId, Long count) {
        Result result = resultRepository.findResultById(resultId);
        List<GetZonesResponseDto.ZoneResponseDto> zones = result.getZones()
                .stream()
                .limit(count)
                .map(GetZonesResponseDto.ZoneResponseDto::from)
                .toList();
        return GetZonesResponseDto.of(zones);
    }
}
