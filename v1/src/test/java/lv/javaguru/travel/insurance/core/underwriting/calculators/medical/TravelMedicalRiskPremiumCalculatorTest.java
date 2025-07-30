package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskPremiumCalculatorTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private AgeCoefficientCalculator ageCoefficientCalculator;

    @Mock
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Mock
    private DayCountCalculator dayCountCalculator;

    @Mock
    private MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @InjectMocks
    private TravelMedicalRiskPremiumCalculator travelMedicalRiskPremiumCalculator;

    @Test
    public void testCalculatePremiumResult() {
        BigDecimal daysCount = BigDecimal.valueOf(10);
        BigDecimal countryDayRate = BigDecimal.valueOf(3.5);
        BigDecimal ageCoefficient = BigDecimal.valueOf(1.1);
        BigDecimal medicalRiskLimitLevel = BigDecimal.valueOf(1.2);

        when(dayCountCalculator.calculateDayCount(request)).thenReturn(daysCount);
        when(countryDefaultDayRateCalculator.findCountryDefaultDayRate(request)).thenReturn(countryDayRate);
        when(ageCoefficientCalculator.calculateAgeCoefficient(request)).thenReturn(ageCoefficient);
        when(medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(request)).thenReturn(medicalRiskLimitLevel);


        BigDecimal expectedPremium = countryDayRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .multiply(medicalRiskLimitLevel)
                .setScale(2, RoundingMode.HALF_UP);

        // Act
        BigDecimal actualPremium = travelMedicalRiskPremiumCalculator.calculatePremium(request);

        assertEquals(expectedPremium, actualPremium);
    }
}
