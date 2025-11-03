package lv.javaguru.travel.insurance.core.repositories.v1;

import lv.javaguru.travel.insurance.core.domain.TMAgeCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TMAgeCoefficientRepositoryV1 extends JpaRepository<TMAgeCoefficient, Long> {

    @Cacheable(value = "tmAgeCoefficientV1", key = "#p0", cacheManager = "cacheManagerV1")
    @Query("SELECT ac from TMAgeCoefficient ac " +
    "where ac.ageFrom <= :age " +
    "and ac.ageTo >= :age")
    Optional<TMAgeCoefficient> findByCoefficient(@Param("age")Integer age);
}
