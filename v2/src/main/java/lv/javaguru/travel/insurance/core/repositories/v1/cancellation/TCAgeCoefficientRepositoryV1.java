package lv.javaguru.travel.insurance.core.repositories.v1.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TCAgeCoefficientRepositoryV1 extends JpaRepository<TCAgeCoefficient, Long> {

    @Cacheable(value = "tcAgeCoefficientV1", key = "#p0", cacheManager = "cacheManagerV1")
    @Query("Select ac from TCAgeCoefficient ac " +
    "where ac.ageFrom <= :age " +
    "and ac.ageTo >= :age")
    Optional<TCAgeCoefficient> findCoefficientByAge(@Param("age")Integer age);

}
