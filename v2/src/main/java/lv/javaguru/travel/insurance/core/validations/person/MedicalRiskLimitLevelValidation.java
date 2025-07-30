package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class MedicalRiskLimitLevelValidation extends PersonFieldValidationImpl {

    @Autowired
    private ClassifierValueRepository classifierValueRepository;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (medicalRiskLimitLevelNotNull(person) && !isInDatabase(person.getMedicalRiskLimitLevel()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean medicalRiskLimitLevelNotNull(PersonDTO person) {
        return (person.getMedicalRiskLimitLevel() != null && !person.getMedicalRiskLimitLevel().isBlank());
    }

    private boolean isInDatabase(String medicalRiskLimitLevelIc) {
        return (classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiskLimitLevelIc).isPresent());
    }


}

