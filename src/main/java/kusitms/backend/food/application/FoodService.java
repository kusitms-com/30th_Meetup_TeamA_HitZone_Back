package kusitms.backend.food.application;

import kusitms.backend.food.domain.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional(readOnly = true)
    public Object getSuitableFoods(String stadiumName, String boundary, String course) {

    }
}
