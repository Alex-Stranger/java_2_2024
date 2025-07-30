package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.repositories.TMCountryDefaultDayRateRepository;

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
class TMCountryDefaultDayRateCalculatorTest {

    @Mock
    private TMCountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @InjectMocks
    private TMCountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Test
    public void testCountryDefaultDayRateSuccess() {
        String countryIc = "JAPAN";
        BigDecimal expectedCountryDayRate = BigDecimal.valueOf(3.5);
        AgreementDTO agreement = new AgreementDTO();
        agreement.setCountry(countryIc);
        TMCountryDefaultDayRate countryDefaultDayRate = new TMCountryDefaultDayRate();
        countryDefaultDayRate.setDefaultDayRate(expectedCountryDayRate);
        when(countryDefaultDayRateRepository.findByCountryIc(countryIc))
                .thenReturn(Optional.of(countryDefaultDayRate));
        BigDecimal actualRate = countryDefaultDayRateCalculator.findCountryDefaultDayRate(agreement);
        assertEquals(expectedCountryDayRate, actualRate);
    }

    @Test
    public void testCountryDefaultDayRateNotFound() {
        String countryIc = "UNKNOWN";
        AgreementDTO agreement = new AgreementDTO();
        agreement.setCountry(countryIc);
        when(countryDefaultDayRateRepository.findByCountryIc(countryIc))
                .thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> countryDefaultDayRateCalculator.findCountryDefaultDayRate(agreement));
        assertEquals("Country not found: " + countryIc, exception.getMessage());
    }
}
