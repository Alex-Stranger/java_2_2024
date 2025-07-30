package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgreementPersonsPremiumCalculatorTest {

    @Mock
    private TravelPremiumUnderwriting premiumUnderwriting;

    @InjectMocks
    private AgreementPersonsPremiumCalculator calculator;

    @Test
    void calculateRiskPremiumsForAllPersons_ShouldReturnUpdatedPersons() {
        AgreementDTO agreement = new AgreementDTO();
        PersonDTO person1 = new PersonDTO();
        PersonDTO person2 = new PersonDTO();
        agreement.setPersons(List.of(person1, person2));

        RiskDTO risk1 = new RiskDTO("Risk1", BigDecimal.valueOf(50));
        RiskDTO risk2 = new RiskDTO("Risk2", BigDecimal.valueOf(70));
        RiskDTO risk3 = new RiskDTO("Risk3", BigDecimal.valueOf(100));

        TravelPremiumCalculationResult result1 = new TravelPremiumCalculationResult(
                BigDecimal.valueOf(100), List.of(risk1, risk2)
        );
        TravelPremiumCalculationResult result2 = new TravelPremiumCalculationResult(
                BigDecimal.valueOf(200), List.of(risk3)
        );
        when(premiumUnderwriting.calculatePremium(agreement, person1)).thenReturn(result1);
        when(premiumUnderwriting.calculatePremium(agreement, person2)).thenReturn(result2);
        List<PersonDTO> updatedPersons = calculator.calculateRiskPremiums(agreement);
        assertEquals(2, updatedPersons.size());
        assertEquals(List.of(risk1, risk2), updatedPersons.get(0).getRisks());
        assertEquals(List.of(risk3), updatedPersons.get(1).getRisks());
        verify(premiumUnderwriting, times(1)).calculatePremium(agreement, person1);
        verify(premiumUnderwriting, times(1)).calculatePremium(agreement, person2);
        verifyNoMoreInteractions(premiumUnderwriting);
    }
}
