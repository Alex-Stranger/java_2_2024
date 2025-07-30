package lv.javaguru.travel.insurance.core.validations;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelPersonFieldValidatorTest {

    @InjectMocks
    private TravelPersonFieldValidator validator;

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private PersonFieldValidation validation1;

    @Mock
    private PersonFieldValidation validation2;

    @Test
    public void shouldNotReturnErrors() {
        when(validation1.validate(agreement, person)).thenReturn(Optional.empty());
        when(validation1.validateList(agreement, person)).thenReturn(List.of());
        when(validation2.validate(agreement, person)).thenReturn(Optional.empty());
        when(validation2.validateList(agreement, person)).thenReturn(List.of());
        when(agreement.getPersons()).thenReturn(List.of(person));
        List<PersonFieldValidation> personValidations = List.of(validation1, validation2);
        ReflectionTestUtils.setField(validator, "personFieldValidations", personValidations);
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnSinglePersonErrors() {
        when(validation1.validate(agreement, person)).thenReturn(Optional.of(new ValidationErrorDTO()));
        when(validation2.validate(agreement, person)).thenReturn(Optional.of(new ValidationErrorDTO()));
        when(agreement.getPersons()).thenReturn(List.of(person));
        List<PersonFieldValidation> personValidations = List.of(validation1, validation2);
        ReflectionTestUtils.setField(validator, "personFieldValidations", personValidations);
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(2, errors.size());
    }

    @Test
    public void shouldReturnListPersonErrors() {
        when(validation1.validate(agreement, person)).thenReturn(Optional.empty());
        when(validation1.validateList(agreement, person)).thenReturn(List.of(new ValidationErrorDTO()));
        when(validation2.validate(agreement, person)).thenReturn(Optional.empty());
        when(validation2.validateList(agreement, person)).thenReturn(List.of(new ValidationErrorDTO()));
        when(agreement.getPersons()).thenReturn(List.of(person));
        List<PersonFieldValidation> personValidations = List.of(validation1, validation2);
        ReflectionTestUtils.setField(validator, "personFieldValidations", personValidations);
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(2, errors.size());
    }

    @Test
    public void shouldReturnCombinedErrors() {
        when(validation1.validate(agreement, person)).thenReturn(Optional.of(new ValidationErrorDTO()));
        when(validation1.validateList(agreement, person)).thenReturn(List.of(new ValidationErrorDTO()));
        when(validation2.validate(agreement, person)).thenReturn(Optional.of(new ValidationErrorDTO()));
        when(validation2.validateList(agreement, person)).thenReturn(List.of(new ValidationErrorDTO()));
        when(agreement.getPersons()).thenReturn(List.of(person));
        List<PersonFieldValidation> personValidations = List.of(validation1, validation2);
        ReflectionTestUtils.setField(validator, "personFieldValidations", personValidations);
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(4, errors.size());
    }
}