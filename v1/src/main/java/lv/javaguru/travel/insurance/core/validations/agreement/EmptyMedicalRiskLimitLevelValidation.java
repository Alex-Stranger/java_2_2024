package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyMedicalRiskLimitLevelValidation extends AgreementFieldValidationImpl {

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (isMedicalRiskLimitLevelEnabled() && containsTravelMedical(request) && medicalRiskLimitLevelIsNullOrEmpty(request))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean containsTravelMedical(TravelCalculatePremiumRequestV1 request) {
        return (request.getSelectedRisks() != null && request.getSelectedRisks().contains("TRAVEL_MEDICAL"));
    }

    private boolean medicalRiskLimitLevelIsNullOrEmpty(TravelCalculatePremiumRequestV1 request) {
        return (request.getMedicalRiskLimitLevel() == null || request.getMedicalRiskLimitLevel().isBlank());
    }
}
