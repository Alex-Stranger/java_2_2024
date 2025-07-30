package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

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
class CountryDefaultDayRateCalculatorTest {

    @Mock
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    @InjectMocks
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Test
    public void testCountryDefaultDayRateSuccess() {
        String countryIc = "JAPAN";
        BigDecimal expectedCountryDayRate = BigDecimal.valueOf(3.5);
        TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();
        request.setCountry(countryIc);
        CountryDefaultDayRate countryDefaultDayRate = new CountryDefaultDayRate();
        countryDefaultDayRate.setDefaultDayRate(expectedCountryDayRate);
        when(countryDefaultDayRateRepository.findByCountryIc(countryIc))
                .thenReturn(Optional.of(countryDefaultDayRate));
        BigDecimal actualRate = countryDefaultDayRateCalculator.findCountryDefaultDayRate(request);
        assertEquals(expectedCountryDayRate, actualRate);
    }

    @Test
    public void testCountryDefaultDayRateNotFound() {
        String countryIc = "UNKNOWN";
        TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();
        request.setCountry(countryIc);
        when(countryDefaultDayRateRepository.findByCountryIc(countryIc))
                .thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> countryDefaultDayRateCalculator.findCountryDefaultDayRate(request));
        assertEquals("Country not found: " + countryIc, exception.getMessage());
    }
}
