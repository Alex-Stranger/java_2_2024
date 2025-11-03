package lv.javaguru.travel.insurance.core.repositories.v2.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TCCountrySafetyRatingCoefficientRepositoryV2 extends JpaRepository<TCCountrySafetyRatingCoefficient, Long> {

    @Cacheable(value = "tcCountrySafetyRatingCoefficientV2", key = "#p0", cacheManager = "cacheManagerV2")
    Optional<TCCountrySafetyRatingCoefficient> findCoefficientByCountryIc(String countryIc);

}
