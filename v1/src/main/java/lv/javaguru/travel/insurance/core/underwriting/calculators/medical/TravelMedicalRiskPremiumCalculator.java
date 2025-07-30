package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;

import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    @Autowired
    private AgeCoefficientCalculator ageCoefficientCalculator;
    @Autowired
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @Autowired
    private DayCountCalculator dayCountCalculator;

    @Autowired
    private MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequestV1 request) {
        BigDecimal dayCount = dayCountCalculator.calculateDayCount(request);
        BigDecimal countryDayRate = countryDefaultDayRateCalculator.findCountryDefaultDayRate(request);
        BigDecimal ageCoefficient = ageCoefficientCalculator.calculateAgeCoefficient(request);
        BigDecimal medicalRiskLimitLevelIc = medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(request);
        return countryDayRate
                .multiply(dayCount)
                .multiply(ageCoefficient)
                .multiply(medicalRiskLimitLevelIc)
                .setScale(2, RoundingMode.HALF_UP);


    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }
}
