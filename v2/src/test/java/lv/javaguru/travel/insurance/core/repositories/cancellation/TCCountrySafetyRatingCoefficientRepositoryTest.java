package lv.javaguru.travel.insurance.core.repositories.cancellation;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import lv.javaguru.travel.insurance.core.repositories.v1.cancellation.TCCountrySafetyRatingCoefficientRepositoryV1;
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
class TCCountrySafetyRatingCoefficientRepositoryTest {

    @Autowired
    private TCCountrySafetyRatingCoefficientRepositoryV1 countrySafetyRatingCoefficientRepository;

    @Test
    public void ifCountrySafetyRatingCoefficientIsSpecified() {
        Optional<TCCountrySafetyRatingCoefficient> result = countrySafetyRatingCoefficientRepository.findCoefficientByCountryIc("JAPAN");
        assertTrue(result.isPresent());
        assertEquals(result.get().getCountryIc(), "JAPAN");
    }

    @Test
    public void ifCountrySafetyRatingCoefficientNotSpecified() {
        Optional<TCCountrySafetyRatingCoefficient> result = countrySafetyRatingCoefficientRepository.findCoefficientByCountryIc(null);
        assertTrue(result.isEmpty());
    }
}
