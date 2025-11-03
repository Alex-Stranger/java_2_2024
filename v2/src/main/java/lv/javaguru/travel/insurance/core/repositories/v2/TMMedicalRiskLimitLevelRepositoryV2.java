package lv.javaguru.travel.insurance.core.repositories.v2;

import lv.javaguru.travel.insurance.core.domain.TMMedicalRiskLimitLevel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TMMedicalRiskLimitLevelRepositoryV2 extends JpaRepository<TMMedicalRiskLimitLevel, Long> {

    @Cacheable(value = "tmMedicalRiskLimitLevelV2", key = "#p0", cacheManager = "cacheManagerV2")
    Optional<TMMedicalRiskLimitLevel> findByMedicalRiskLimitLevelIc(String medicalRiskLimitLevelIc);
}
