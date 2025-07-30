package lv.javaguru.travel.insurance.core.repositories.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TCTravelCostCoefficientRepositoryTest {

    @Autowired
    private TCTravelCostCoefficientRepository travelCostCoefficientRepository;

    @Test
    public void ifTravelCostIsSpecified() {
        Optional<TCTravelCostCoefficient> result = travelCostCoefficientRepository.findCoefficientByTravelCost(BigDecimal.valueOf(4999.99));
        assertTrue(result.isPresent());
        assertEquals(0, result.get().getCoefficient().compareTo(BigDecimal.valueOf(10.0)));
    }

    @Test
    public void ifTravelCostIsNotSpecified() {
        Optional<TCTravelCostCoefficient> result = travelCostCoefficientRepository.findCoefficientByTravelCost(null);
        assertTrue(result.isEmpty());
    }


}
