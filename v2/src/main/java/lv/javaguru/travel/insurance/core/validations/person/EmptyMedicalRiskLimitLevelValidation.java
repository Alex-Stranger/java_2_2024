package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyMedicalRiskLimitLevelValidation extends PersonFieldValidationImpl {

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (isMedicalRiskLimitLevelEnabled() && containsTravelMedical(agreement) && medicalRiskLimitLevelIsNullOrEmpty(person))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelEnabled() {
        return medicalRiskLimitLevelEnabled;
    }

    private boolean containsTravelMedical(AgreementDTO agreement) {
        return (agreement.getSelectedRisks() != null && agreement.getSelectedRisks().contains("TRAVEL_MEDICAL"));
    }

    private boolean medicalRiskLimitLevelIsNullOrEmpty(PersonDTO person) {
        return (person.getMedicalRiskLimitLevel() == null || person.getMedicalRiskLimitLevel().isBlank());
    }
}
