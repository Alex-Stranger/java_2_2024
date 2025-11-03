package lv.javaguru.travel.insurance.core.repositories.v1;

import lv.javaguru.travel.insurance.core.domain.TMMedicalRiskLimitLevel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TMMedicalRiskLimitLevelRepositoryV1 extends JpaRepository<TMMedicalRiskLimitLevel, Long> {

    @Cacheable(value = "tmMedicalRiskLimitLevelV1", key = "#p0", cacheManager = "cacheManagerV1")
    Optional<TMMedicalRiskLimitLevel> findByMedicalRiskLimitLevelIc(String medicalRiskLimitLevelIc);
}
