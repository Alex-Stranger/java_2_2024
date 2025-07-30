package lv.javaguru.travel.insurance.core.underwriting;


import lv.javaguru.travel.insurance.dto.RiskPremium;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class SelectedRisksPremiumCalculator {

    @Autowired
    private List<TravelRiskPremiumCalculator> riskPremiumCalculators;

    List<RiskPremium> calculateAllRiskPremium(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks().stream()
                .map(riskIc -> {
                    BigDecimal riskPremium = calculateRiskPremium(riskIc, request);
                    return new RiskPremium(riskIc, riskPremium);})
                .toList();
    }

    private BigDecimal calculateRiskPremium(String riskIc, TravelCalculatePremiumRequestV1 request) {
        var riskPremiumCalculator = findRiskPremiumCalculator(riskIc);
        return riskPremiumCalculator.calculatePremium(request);
    }

    private TravelRiskPremiumCalculator findRiskPremiumCalculator(String riskIc) {
        return riskPremiumCalculators.stream()
                .filter(riskCalculator -> riskCalculator.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported riskIc = " + riskIc));
    }
}
