package lv.javaguru.travel.insurance.core.repositories;

import lv.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TMCountryDefaultDayRateRepositoryTest {

    @Autowired
    private TMCountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @Test
    public void ifCountryIsSpecified() {
        Optional<TMCountryDefaultDayRate> result = countryDefaultDayRateRepository.findByCountryIc("JAPAN");
        assertTrue(result.isPresent());
        assertEquals(result.get().getCountryIc(), "JAPAN");
    }

    @Test
    public void ifCountryNotSpecified() {
        Optional<TMCountryDefaultDayRate> result = countryDefaultDayRateRepository.findByCountryIc(null);
        assertTrue(result.isEmpty());
    }
}
