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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonFirstNameValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonFirstNameValidation validation;

    @Test
    public void returnErrorWhenPersonFirstNameIsNull() {
        PersonDTO person = mock(PersonDTO.class);
        when(person.getPersonFirstName()).thenReturn(null);
        ValidationErrorDTO validationError = mock(ValidationErrorDTO.class);
        when(validationErrorFactory.buildError("ERROR_CODE_7")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void returnErrorWhenPersonFirstNameIsEmpty() {
        PersonDTO person = mock(PersonDTO.class);
        when(person.getPersonFirstName()).thenReturn("");
        ValidationErrorDTO validationError = mock(ValidationErrorDTO.class);
        when(validationErrorFactory.buildError("ERROR_CODE_7")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void notReturnErrorIfThereIsPersonName() {
        PersonDTO person = mock(PersonDTO.class);
        when(person.getPersonFirstName()).thenReturn("Tom");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }
}
