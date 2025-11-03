package lv.javaguru.travel.insurance.core.repositories.v1.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TCCountrySafetyRatingCoefficientRepositoryV1 extends JpaRepository<TCCountrySafetyRatingCoefficient, Long> {

    @Cacheable(value = "tcCountrySafetyRatingCoefficientV1", key = "#p0", cacheManager = "cacheManagerV1")
    Optional<TCCountrySafetyRatingCoefficient> findCoefficientByCountryIc(String countryIc);

}
