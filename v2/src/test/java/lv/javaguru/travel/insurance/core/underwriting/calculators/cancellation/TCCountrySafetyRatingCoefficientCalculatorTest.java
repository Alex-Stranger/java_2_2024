package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCCountrySafetyRatingCoefficientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TCCountrySafetyRatingCoefficientCalculatorTest {

    @Mock
    private TCCountrySafetyRatingCoefficientRepository countrySafetyRatingCoefficientRepository;

    @InjectMocks
    private TCCountrySafetyRatingCoefficientCalculator countrySafetyRatingCoefficientCalculator;

    @Test
    public void countrySafetyRatingCoefficientSuccess() {
        String countryIc = "JAPAN";
        BigDecimal expectedCountrySafetyRatingCoefficient = BigDecimal.valueOf(9.0);
        AgreementDTO agreement = new AgreementDTO();
        agreement.setCountry(countryIc);
        TCCountrySafetyRatingCoefficient countrySafetyRatingCoefficient = new TCCountrySafetyRatingCoefficient();
        countrySafetyRatingCoefficient.setCoefficient(expectedCountrySafetyRatingCoefficient);
        when(countrySafetyRatingCoefficientRepository.findCoefficientByCountryIc(countryIc)).thenReturn(Optional.of(countrySafetyRatingCoefficient));
        BigDecimal result = countrySafetyRatingCoefficientCalculator.findCountrySafetyRatingCoefficient(agreement);
        assertEquals(expectedCountrySafetyRatingCoefficient, result);
    }

    @Test
    public void countrySafetyRatingCoefficientNotFound() {
        String countryIc = "UNKNOWN";
        AgreementDTO agreement = new AgreementDTO();
        agreement.setCountry(countryIc);
        when(countrySafetyRatingCoefficientRepository.findCoefficientByCountryIc(countryIc)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> countrySafetyRatingCoefficientCalculator.findCountrySafetyRatingCoefficient(agreement));
        assertEquals("Country safety rating coefficient not found: " + countryIc, exception.getMessage());
    }
}
