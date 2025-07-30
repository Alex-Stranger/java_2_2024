package lv.javaguru.travel.insurance.core.repositories.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface TCTravelCostCoefficientRepository extends JpaRepository<TCTravelCostCoefficient, Long> {

    @Query("Select tc from TCTravelCostCoefficient tc " +
    "where tc.travelCostFrom <= :travelCost " +
    "and tc.travelCostTo >= :travelCost")
    Optional<TCTravelCostCoefficient> findCoefficientByTravelCost(@Param("travelCost")BigDecimal travelCost);
}
