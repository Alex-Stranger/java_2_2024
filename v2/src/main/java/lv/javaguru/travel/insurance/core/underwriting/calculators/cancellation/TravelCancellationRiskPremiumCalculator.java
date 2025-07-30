package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
class TravelCancellationRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    @Autowired
    private TCTravelCostCoefficientCalculator travelCostCoefficientCalculator;

    @Autowired
    private TCAgeCoefficientCalculator ageCoefficientCalculator;

    @Autowired
    private TCCountrySafetyRatingCoefficientCalculator countrySafetyRatingCoefficientCalculator;

    @Override
    public BigDecimal calculatePremium(AgreementDTO agreement, PersonDTO person) {
        BigDecimal travelCost = travelCostCoefficientCalculator.calculateTravelCost(person);
        BigDecimal ageCoefficient = ageCoefficientCalculator.ageCoefficient(person);
        BigDecimal countrySafetyRating = countrySafetyRatingCoefficientCalculator.findCountrySafetyRatingCoefficient(agreement);
        return travelCost
                .add(ageCoefficient)
                .add(countrySafetyRating)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_CANCELLATION";
    }
}
