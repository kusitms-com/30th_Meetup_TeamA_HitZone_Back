package kusitms.backend.result.domain.service;

import kusitms.backend.result.domain.enums.ProfileStatusType;
import kusitms.backend.result.domain.model.Profile;
import kusitms.backend.result.domain.model.Result;
import kusitms.backend.result.domain.model.Zone;
import kusitms.backend.stadium.domain.enums.StadiumStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ResultDomainService {

    /**
     * 정보들을 토대로 Result 객체를 생성한다. (Result 하위의 Profile, Zone 엔티티는 Result 를 통해 생성, 수정)
     * @param userId 구역 추천을 받은 유저의 유저 id (익명일 경우 null)
     * @param stadiumId 해당하는 구장의 구장 id
     * @param preference 선호 구역 (1루석 or 3루석)
     * @param recommendedProfile 추천 결과로 받은 프로필 정보
     * @param recommendedZones 추천 결과로 받은 구역 정보 리스트
     * @return 결과 객체 Result
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    public <T extends Enum<T> & StadiumStatusType> Result buildResult(
            Long userId, Long stadiumId, String preference,
            ProfileStatusType recommendedProfile, List<T> recommendedZones) {

        Result result = Result.toDomain(
                null,
                userId,
                stadiumId,
                preference
        );

        Profile profile = buildProfile(recommendedProfile);
        result.addProfile(profile);

        List<Zone> zones = buildZones(recommendedZones);
        zones.forEach(result::addZone);

        log.info("Result generated according to conditions completed");
        return result;
    }

    /**
     * 프로필 정보를 토대로 프로필 객체를 반환한다.
     * @param recommendedProfile 추천 결과로 받은 프로필 정보
     * @return Profile 객체
     */
    private Profile buildProfile(ProfileStatusType recommendedProfile) {
        return Profile.toDomain(
                    null,
                        recommendedProfile.getImgUrl(),
                        recommendedProfile.getNickName(),
                        recommendedProfile.getType(),
                        recommendedProfile.getHashTags()
                );
    }

    /**
     * 구역 정보 리스트를 토대로 구역 객체 리스트를 반환한다.
     * @param recommendedZones 추천 결과로 받은 구역 정보 리스트
     * @return Zone 객체 리스트
     * @param <T> StadiumStatusType 인터페이스를 상속한 ENUM 형식
     */
    private <T extends Enum<T> & StadiumStatusType> List<Zone> buildZones(List<T> recommendedZones) {
        return recommendedZones.stream()
                .map(zoneEnum -> Zone.toDomain(
                        null,
                                zoneEnum.getZoneName(),
                                zoneEnum.getZoneColor(),
                                zoneEnum.getExplanations(),
                                zoneEnum.getTip(),
                                zoneEnum.getReferencesGroup()
                        ))
                .toList();
    }
}
