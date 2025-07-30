package lv.javaguru.travel.insurance.core.underwriting;

import lv.javaguru.travel.insurance.dto.RiskPremium;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPremiumUnderwritingTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @InjectMocks
    private TravelPremiumUnderwritingImpl premiumUnderwriting;

    @Test
    public void calculateTotalPremium() {
        RiskPremium riskPremium1 = new RiskPremium("TRAVEL_MEDICAL", BigDecimal.ONE);
        RiskPremium riskPremium2 = new RiskPremium("TRAVEL_CANCELLATION", BigDecimal.ONE);
        List<RiskPremium> riskPremiums = List.of(riskPremium1, riskPremium2);
        when(selectedRisksPremiumCalculator.calculateAllRiskPremium(request)).thenReturn(riskPremiums);
        TravelPremiumCalculationResult result = premiumUnderwriting.calculatePremium(request);
        assertEquals(new BigDecimal(2), result.getTotalPremium());
    }





}