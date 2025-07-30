package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.domain.AgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Component
class AgeCoefficientCalculator {

    @Value("${medical.risk.age.coefficient.enabled:false}")
    private Boolean medicalRiskAgeCoefficientEnabled;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private AgeCoefficientRepository ageCoefficientRepository;

    BigDecimal calculateAgeCoefficient(TravelCalculatePremiumRequestV1 request) {
        return medicalRiskAgeCoefficientEnabled
                ? findAgeCoefficient(request)
                : getDefaultValue();
    }

    public BigDecimal findAgeCoefficient(TravelCalculatePremiumRequestV1 request) {
        int age = calculateAge(request);
        return ageCoefficientRepository.findByCoefficient(age)
                .map(AgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Age coefficient not found: " + age));
    }

    private int calculateAge(TravelCalculatePremiumRequestV1 request) {
        LocalDate birthDate = request.getPersonBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = dateTimeUtil.currentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(birthDate, currentDate).getYears();
    }

    private BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }
}
