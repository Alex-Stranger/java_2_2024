package lv.javaguru.travel.insurance.core.repositories.v2;

import lv.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TMCountryDefaultDayRateRepositoryV2 extends JpaRepository<TMCountryDefaultDayRate, Long> {

    @Cacheable(value = "tmCountryDefaultDayRateV2", key = "#p0", cacheManager = "cacheManagerV2")
    Optional<TMCountryDefaultDayRate> findByCountryIc(String countryIc);
}
