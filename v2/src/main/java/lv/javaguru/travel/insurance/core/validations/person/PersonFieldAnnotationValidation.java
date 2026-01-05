package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.Placeholder;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
class PersonFieldAnnotationValidation extends PersonFieldValidationImpl {

    @Autowired
    private Validator validator;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreement , PersonDTO person) {
        List<ValidationErrorDTO> errors = new ArrayList<>();

        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(person);
        for (ConstraintViolation<PersonDTO> violation : violations) {
            String message = violation.getMessage();
            Placeholder placeholder = new Placeholder("ANNOTATION VALIDATION MESSAGE", message);
            errors.add(validationErrorFactory.buildError("ERROR_CODE_26", List.of(placeholder)));
        }
        return errors;
    }
}
