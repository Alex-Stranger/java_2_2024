package lv.javaguru.travel.insurance.core.repositories.cancellation;

import lv.javaguru.travel.insurance.core.domain.TMAgeCoefficient;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
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
class TCAgeCoefficientRepositoryTest {

    @Autowired
    private TCAgeCoefficientRepository ageCoefficientRepository;

    @Test
    public void ifAgeIsSpecified() {
        Optional<TCAgeCoefficient> result = ageCoefficientRepository.findCoefficientByAge(30);
        assertTrue(result.isPresent());
        assertEquals(0, result.get().getCoefficient().compareTo(new BigDecimal("20.0")));
    }

    @Test
    public void ifAgeNotSpecified() {
        Optional<TCAgeCoefficient> result = ageCoefficientRepository.findCoefficientByAge(null);
        assertTrue(result.isEmpty());
    }
}
