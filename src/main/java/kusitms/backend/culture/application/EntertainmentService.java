package kusitms.backend.culture.application;

import kusitms.backend.culture.domain.repository.EntertainmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntertainmentService {

    private final EntertainmentRepository entertainmentRepository;


}
