package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonFieldAnnotationValidationTest {

    @Mock
    private Validator validator;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonFieldAnnotationValidation validation;

    @Test
    public void returnErrorWithOneViolation() {
        PersonDTO person = new PersonDTO();
        AgreementDTO agreement = new AgreementDTO();
        ConstraintViolation<PersonDTO> violation = mock(ConstraintViolation.class);
        when(validator.validate(person)).thenReturn(Set.of(violation));
        when(violation.getMessage()).thenReturn("ANNOTATION VALIDATION MESSAGE");
        ValidationErrorDTO error = new ValidationErrorDTO();
        when(validationErrorFactory.buildError(eq("ERROR_CODE_26"), anyList())).thenReturn(error);
        List<ValidationErrorDTO> errors = validation.validateList(agreement, person);
        assertEquals(1, errors.size());

    }

    @Test
    public void returnErrorWithTwoViolations() {
        PersonDTO person = new PersonDTO();
        AgreementDTO agreement = new AgreementDTO();
        ConstraintViolation<PersonDTO> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<PersonDTO> violation2 = mock(ConstraintViolation.class);
        when(validator.validate(person)).thenReturn(Set.of(violation1, violation2));
        when(violation1.getMessage()).thenReturn("ANNOTATION VALIDATION MESSAGE");
        when(violation2.getMessage()).thenReturn("ANNOTATION VALIDATION MESSAGE");
        ValidationErrorDTO error = new ValidationErrorDTO();
        when(validationErrorFactory.buildError(eq("ERROR_CODE_26"), anyList())).thenReturn(error);
        List<ValidationErrorDTO> errors = validation.validateList(agreement, person);
        assertEquals(2, errors.size());

    }

    @Test
    public void notReturnErrorWhenNoViolations() {
        PersonDTO person = new PersonDTO();
        AgreementDTO agreement = new AgreementDTO();
        when(validator.validate(person)).thenReturn(Set.of());
        List<ValidationErrorDTO> errors = validation.validateList(agreement, person);
        assertTrue(errors.isEmpty());
    }
}
