package lv.javaguru.travel.insurance.core.repositories.v1;

import lv.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TMCountryDefaultDayRateRepositoryV1 extends JpaRepository<TMCountryDefaultDayRate, Long> {

    @Cacheable(value = "tmCountryDefaultDayRateV1", key = "#p0", cacheManager = "cacheManagerV1")
    Optional<TMCountryDefaultDayRate> findByCountryIc(String countryIc);
}
