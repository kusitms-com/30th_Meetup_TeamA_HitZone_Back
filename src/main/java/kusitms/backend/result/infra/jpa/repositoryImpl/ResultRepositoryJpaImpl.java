package kusitms.backend.result.infra.jpa.repositoryImpl;

import kusitms.backend.global.exception.CustomException;
import kusitms.backend.result.domain.model.Result;
import kusitms.backend.result.domain.repository.ResultRepository;
import kusitms.backend.result.infra.jpa.entity.ResultEntity;
import kusitms.backend.result.infra.jpa.repository.ResultJpaRepository;
import kusitms.backend.result.infra.mapper.ResultMapper;
import kusitms.backend.result.status.ResultErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ResultRepositoryJpaImpl implements ResultRepository {

    private final ResultJpaRepository resultJpaRepository;

    @Override
    public Result saveResult(Result result) {
        ResultEntity resultEntity = resultJpaRepository.save(ResultMapper.toEntity(result));
        return ResultMapper.toDomain(resultEntity);
    }

    @Override
    public Result findResultById(Long id) {
        return resultJpaRepository.findById(id)
                .map(ResultMapper::toDomain)
                .orElseThrow(() -> new CustomException(ResultErrorStatus._NOT_FOUND_RESULT));
    }
}



