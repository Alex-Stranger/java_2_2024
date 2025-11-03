package lv.javaguru.travel.insurance.core.repositories.v2.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface TCTravelCostCoefficientRepositoryV2 extends JpaRepository<TCTravelCostCoefficient, Long> {

    @Cacheable(value = "tcTravelCostCoefficientV2", key = "#p0", cacheManager = "cacheManagerV2")
    @Query("Select tc from TCTravelCostCoefficient tc " +
    "where tc.travelCostFrom <= :travelCost " +
    "and tc.travelCostTo >= :travelCost")
    Optional<TCTravelCostCoefficient> findCoefficientByTravelCost(@Param("travelCost")BigDecimal travelCost);
}
