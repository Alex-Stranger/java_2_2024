package lv.javaguru.travel.insurance.core.repositories;

import lv.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AgeCoefficientRepositoryTest {

    @Autowired
    private AgeCoefficientRepository ageCoefficientRepository;

    @Test
    public void ifAgeIsSpecified() {
        Optional<AgeCoefficient> result = ageCoefficientRepository.findByCoefficient(15);
        assertTrue(result.isPresent());
        assertEquals(result.get().getCoefficient(), new BigDecimal("1.00"));
    }

    @Test
    public void ifAgeNotSpecified() {
        Optional<AgeCoefficient> result = ageCoefficientRepository.findByCoefficient(null);
        assertTrue(result.isEmpty());
    }
}
