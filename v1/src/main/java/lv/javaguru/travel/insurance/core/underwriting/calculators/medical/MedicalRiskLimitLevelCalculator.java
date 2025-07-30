package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import lv.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class MedicalRiskLimitLevelCalculator {

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    @Autowired
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    BigDecimal calculateMedicalRiskLimitLevel(TravelCalculatePremiumRequestV1 request) {
        return medicalRiskLimitLevelEnabled
                ? findByMedicalRiskLimitLevelIc(request)
                : getDefaultValue();
    }

    private BigDecimal findByMedicalRiskLimitLevelIc(TravelCalculatePremiumRequestV1 request) {
        String medicalRiskLimitLevelIc = request.getMedicalRiskLimitLevel();
        return medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(medicalRiskLimitLevelIc)
                .map(MedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Medical risk limit level not found: " + medicalRiskLimitLevelIc));
    }

    private BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }
}
