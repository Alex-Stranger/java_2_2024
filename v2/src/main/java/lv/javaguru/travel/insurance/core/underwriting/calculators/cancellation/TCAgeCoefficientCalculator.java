package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCAgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Component
class TCAgeCoefficientCalculator {

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private TCAgeCoefficientRepository ageCoefficientRepository;

    public BigDecimal ageCoefficient(PersonDTO person) {
        int age = calculateAge(person);
        return ageCoefficientRepository.findCoefficientByAge(age)
                .map(TCAgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Age coefficient not found: " + age));

    }

    private int calculateAge(PersonDTO person) {
        LocalDate birthDate = person.getPersonBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = dateTimeUtil.currentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(birthDate, currentDate).getYears();
    }




}
