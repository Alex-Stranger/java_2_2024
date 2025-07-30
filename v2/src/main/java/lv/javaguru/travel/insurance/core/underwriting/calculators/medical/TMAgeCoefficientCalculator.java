package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.TMAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.TMAgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Component
class TMAgeCoefficientCalculator {

    @Value("${medical.risk.age.coefficient.enabled:false}")
    private Boolean medicalRiskAgeCoefficientEnabled;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private TMAgeCoefficientRepository ageCoefficientRepository;

    BigDecimal calculateAgeCoefficient(PersonDTO person) {
        return medicalRiskAgeCoefficientEnabled
                ? findAgeCoefficient(person)
                : getDefaultValue();
    }

    public BigDecimal findAgeCoefficient(PersonDTO person) {
        int age = calculateAge(person);
        return ageCoefficientRepository.findByCoefficient(age)
                .map(TMAgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Age coefficient not found: " + age));
    }

    private int calculateAge(PersonDTO person) {
        LocalDate birthDate = person.getPersonBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = dateTimeUtil.currentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(birthDate, currentDate).getYears();
    }

    private BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }
}
