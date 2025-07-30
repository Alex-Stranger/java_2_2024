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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksPremiumCalculatorTest {

    @Mock
    private List<TravelRiskPremiumCalculator> riskPremiumCalculators;

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private TravelRiskPremiumCalculator riskPremiumCalculator1;

    @Mock
    private TravelRiskPremiumCalculator riskPremiumCalculator2;

    @InjectMocks
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @Test
    public void calculatePremiumForOneRisk() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(riskPremiumCalculators.stream()).thenReturn(List.of(riskPremiumCalculator1).stream());
        when(riskPremiumCalculator1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(riskPremiumCalculator1.calculatePremium(any())).thenReturn(BigDecimal.ONE);
        List<RiskPremium> riskPremiums = selectedRisksPremiumCalculator.calculateAllRiskPremium(request);
        assertEquals(1, riskPremiums.size());
        assertEquals("TRAVEL_MEDICAL", riskPremiums.get(0).getRiskIc());
        assertEquals(BigDecimal.ONE, riskPremiums.get(0).getPremium());
    }

    @Test
    public void calculatePremiumForTwoRisks() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_CANCELLATION"));
        when(riskPremiumCalculators.stream()).thenAnswer(invocationOnMock -> Stream.of(riskPremiumCalculator1, riskPremiumCalculator2));
        when(riskPremiumCalculator1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        when(riskPremiumCalculator1.calculatePremium(any())).thenReturn(BigDecimal.ONE);
        when(riskPremiumCalculator2.getRiskIc()).thenReturn("TRAVEL_CANCELLATION");
        when(riskPremiumCalculator2.calculatePremium(any())).thenReturn(BigDecimal.ONE);
        List<RiskPremium> riskPremiums = selectedRisksPremiumCalculator.calculateAllRiskPremium(request);
        assertEquals(2, riskPremiums.size());
        assertEquals("TRAVEL_MEDICAL", riskPremiums.get(0).getRiskIc());
        assertEquals(BigDecimal.ONE, riskPremiums.get(0).getPremium());
        assertEquals("TRAVEL_CANCELLATION", riskPremiums.get(1).getRiskIc());
        assertEquals(BigDecimal.ONE, riskPremiums.get(1).getPremium());
    }

    @Test
    public void unsupportedRisk() {
        when(request.getSelectedRisks()).thenReturn(List.of("UNSUPPORTED_RISK"));
        when(riskPremiumCalculators.stream()).thenReturn(List.of(riskPremiumCalculator1).stream());
        when(riskPremiumCalculator1.getRiskIc()).thenReturn("TRAVEL_MEDICAL");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> selectedRisksPremiumCalculator.calculateAllRiskPremium(request));
        assertEquals("Not supported riskIc = UNSUPPORTED_RISK", exception.getMessage());
    }


}
