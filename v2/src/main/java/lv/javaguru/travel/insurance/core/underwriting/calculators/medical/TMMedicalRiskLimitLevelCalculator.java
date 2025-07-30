package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.TMMedicalRiskLimitLevel;
import lv.javaguru.travel.insurance.core.repositories.TMMedicalRiskLimitLevelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TMMedicalRiskLimitLevelCalculator {

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    @Autowired
    private TMMedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    BigDecimal calculateMedicalRiskLimitLevel(PersonDTO person) {
        return medicalRiskLimitLevelEnabled
                ? findByMedicalRiskLimitLevelIc(person)
                : getDefaultValue();
    }

    private BigDecimal findByMedicalRiskLimitLevelIc(PersonDTO person) {
        return medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(person.getMedicalRiskLimitLevel())
                .map(TMMedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Medical risk limit level not found: " + person.getMedicalRiskLimitLevel()));
    }

    private BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }
}
