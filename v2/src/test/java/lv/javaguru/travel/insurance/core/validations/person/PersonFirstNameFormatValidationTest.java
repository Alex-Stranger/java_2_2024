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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonFirstNameFormatValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @Mock
    private ValidationErrorDTO validationError;

    @InjectMocks
    private PersonFirstNameFormatValidation validation;

    @Test
    public void returnEmptyWhenPersonFirstNameIsNull() {
        when(person.getPersonFirstName()).thenReturn(null);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void returnEmptyWhenPersonFirstNameIsEmpty() {
        when(person.getPersonFirstName()).thenReturn("");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void returnErrorWhenPersonFirstNameIsIncorrectFormat() {
        when(person.getPersonFirstName()).thenReturn("Марк");
        when(validationErrorFactory.buildError("ERROR_CODE_24")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void notReturnErrorWhenPersonFirstNameIsValidFormat() {
        when(person.getPersonFirstName()).thenReturn("Mark");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }
}
