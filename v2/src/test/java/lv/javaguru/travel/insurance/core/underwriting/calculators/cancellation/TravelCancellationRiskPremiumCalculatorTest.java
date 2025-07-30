package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

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
class TravelCancellationRiskPremiumCalculatorTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private TCTravelCostCoefficientCalculator travelCostCoefficientCalculator;

    @Mock
    private TCAgeCoefficientCalculator ageCoefficientCalculator;

    @Mock
    private TCCountrySafetyRatingCoefficientCalculator countrySafetyRatingCoefficientCalculator;

    @InjectMocks
    private TravelCancellationRiskPremiumCalculator travelCancellationRiskPremiumCalculator;

    @Test
    public void calculatePremiumResult() {
        BigDecimal travelCost = BigDecimal.valueOf(30.0);
        BigDecimal ageCoefficient = BigDecimal.valueOf(20.0);
        BigDecimal countrySafetyRatingCoefficient = BigDecimal.valueOf(9.0);

        when(travelCostCoefficientCalculator.calculateTravelCost(person)).thenReturn(travelCost);
        when(ageCoefficientCalculator.ageCoefficient(person)).thenReturn(ageCoefficient);
        when(countrySafetyRatingCoefficientCalculator.findCountrySafetyRatingCoefficient(agreement)).thenReturn(countrySafetyRatingCoefficient);

        BigDecimal expectedPremium = travelCost
                .add(ageCoefficient)
                .add(countrySafetyRatingCoefficient)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal actualPremium = travelCancellationRiskPremiumCalculator.calculatePremium(agreement, person);
        assertEquals(expectedPremium, actualPremium);


    }
}
