package lv.javaguru.travel.insurance.core.underwriting;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPremiumUnderwritingTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @InjectMocks
    private TravelPremiumUnderwritingImpl premiumUnderwriting;

    @Test
    public void calculateTotalPremium() {
        RiskDTO riskPremium1 = new RiskDTO("TRAVEL_MEDICAL", BigDecimal.ONE);
        RiskDTO riskPremium2 = new RiskDTO("TRAVEL_CANCELLATION", BigDecimal.ONE);
        List<RiskDTO> riskPremiums = List.of(riskPremium1, riskPremium2);
        when(selectedRisksPremiumCalculator.calculateAllRiskPremium(agreement, person)).thenReturn(riskPremiums);
        TravelPremiumCalculationResult result = premiumUnderwriting.calculatePremium(agreement, person);
        assertEquals(new BigDecimal(2), result.getTotalPremium());
    }





}