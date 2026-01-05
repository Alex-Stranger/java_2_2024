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
class PersonLastNameFormatValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @Mock
    private ValidationErrorDTO validationError;

    @InjectMocks
    private PersonLastNameFormatValidation validation;

    @Test
    public void returnEmptyWhenPersonLastNameIsNull() {
        when(person.getPersonLastName()).thenReturn(null);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void returnEmptyWhenPersonLastNameIsEmpty() {
        when(person.getPersonLastName()).thenReturn("");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void returnErrorWhenPersonLastNameIsIncorrectFormat() {
        when(person.getPersonLastName()).thenReturn("Твейн");
        when(validationErrorFactory.buildError("ERROR_CODE_25")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void notReturnErrorWhenPersonLastNameIsValidFormat() {
        when(person.getPersonLastName()).thenReturn("Twain");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }
}
