package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;

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
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private TMAgeCoefficientCalculator ageCoefficientCalculator;

    @Mock
    private TMCountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Mock
    private DayCountCalculator dayCountCalculator;

    @Mock
    private TMMedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @InjectMocks
    private TravelMedicalRiskPremiumCalculator travelMedicalRiskPremiumCalculator;

    @Test
    public void testCalculatePremiumResult() {
        BigDecimal daysCount = BigDecimal.valueOf(10);
        BigDecimal countryDayRate = BigDecimal.valueOf(3.5);
        BigDecimal ageCoefficient = BigDecimal.valueOf(1.1);
        BigDecimal medicalRiskLimitLevel = BigDecimal.valueOf(1.2);

        when(dayCountCalculator.calculateDayCount(agreement)).thenReturn(daysCount);
        when(countryDefaultDayRateCalculator.findCountryDefaultDayRate(agreement)).thenReturn(countryDayRate);
        when(ageCoefficientCalculator.calculateAgeCoefficient(person)).thenReturn(ageCoefficient);
        when(medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(person)).thenReturn(medicalRiskLimitLevel);


        BigDecimal expectedPremium = countryDayRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .multiply(medicalRiskLimitLevel)
                .setScale(2, RoundingMode.HALF_UP);

        // Act
        BigDecimal actualPremium = travelMedicalRiskPremiumCalculator.calculatePremium(agreement, person);

        assertEquals(expectedPremium, actualPremium);
    }
}
