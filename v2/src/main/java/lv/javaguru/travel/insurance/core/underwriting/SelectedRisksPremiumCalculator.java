package lv.javaguru.travel.insurance.core.underwriting;


import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class SelectedRisksPremiumCalculator {

    @Autowired
    private List<TravelRiskPremiumCalculator> riskPremiumCalculators;

    List<RiskDTO> calculateAllRiskPremium(AgreementDTO agreement, PersonDTO person) {
        return agreement.getSelectedRisks().stream()
                .map(riskIc -> {
                    BigDecimal riskPremium = calculateRiskPremium(riskIc, agreement, person);
                    return new RiskDTO(riskIc, riskPremium);})
                .toList();
    }

    private BigDecimal calculateRiskPremium(String riskIc, AgreementDTO agreement, PersonDTO person) {
        var riskPremiumCalculator = findRiskPremiumCalculator(riskIc);
        return riskPremiumCalculator.calculatePremium(agreement, person);
    }

    private TravelRiskPremiumCalculator findRiskPremiumCalculator(String riskIc) {
        return riskPremiumCalculators.stream()
                .filter(riskCalculator -> riskCalculator.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported riskIc = " + riskIc));
    }
}
