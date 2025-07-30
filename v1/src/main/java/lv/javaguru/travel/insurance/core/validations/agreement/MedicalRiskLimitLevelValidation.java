package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class MedicalRiskLimitLevelValidation extends AgreementFieldValidationImpl {

    @Autowired
    private ClassifierValueRepository classifierValueRepository;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (medicalRiskLimitLevelNotNull(request) && !isInDatabase(request.getMedicalRiskLimitLevel()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean medicalRiskLimitLevelNotNull(TravelCalculatePremiumRequestV1 request) {
        return (request.getMedicalRiskLimitLevel() != null && !request.getMedicalRiskLimitLevel().isBlank());
    }

    private boolean isInDatabase(String medicalRiskLimitLevelIc) {
        return (classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiskLimitLevelIc).isPresent());
    }


}

