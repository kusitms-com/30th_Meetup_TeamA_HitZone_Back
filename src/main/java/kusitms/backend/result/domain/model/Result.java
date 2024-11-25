package kusitms.backend.result.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result {

    private Long id;
    private Long userId; // User를 ID로 간접 참조
    private Long stadiumId; // Stadium을 ID로 간접 참조
    private String preference;
    private Profile profile;
    private List<Zone> zones = new ArrayList<>();

    public Result(Long id,
                  Long userId,
                  Long stadiumId,
                  String preference
    ) {
        this.id = id;
        this.userId = userId;
        this.stadiumId = stadiumId;
        this.preference = preference;
    }

    public static Result toDomain(
            Long id,
            Long userId,
            Long stadiumId,
            String preference
    ) {
        return new Result(id, userId, stadiumId, preference);
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
        profile.assignToResult(this);
    }

    public void addZone(Zone zone){
        this.zones.add(zone);
        zone.assignToResult(this);
    }
}
