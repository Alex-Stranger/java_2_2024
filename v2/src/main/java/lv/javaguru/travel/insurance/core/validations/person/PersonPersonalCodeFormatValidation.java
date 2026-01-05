package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PersonPersonalCodeFormatValidation extends PersonFieldValidationImpl {

    private static final String PERSONAL_CODE_REGEX = "^\\d{6}-\\d{5}$";

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        if (person.getPersonalCode() == null || person.getPersonalCode().isEmpty()) {
            return Optional.empty();
        }
        if (!person.getPersonalCode().matches(PERSONAL_CODE_REGEX)) {
            return Optional.of(validationErrorFactory.buildError("ERROR_CODE_23"));
        }
        return Optional.empty();
    }
}
