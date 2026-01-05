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
class PersonPersonalCodeFormatValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @Mock
    private ValidationErrorDTO validationError;

    @InjectMocks
    private PersonPersonalCodeFormatValidation validation;

    @Test
    public void returnEmptyWhenPersonPersonalCodeIsNull() {
        when(person.getPersonalCode()).thenReturn(null);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void returnEmptyWhenPersonPersonalCodeIsEmpty() {
        when(person.getPersonalCode()).thenReturn("");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void returnErrorWhenPersonPersonalCodeIsIncorrectFormat() {
        when(person.getPersonalCode()).thenReturn("08051965-15728");
        when(validationErrorFactory.buildError("ERROR_CODE_23")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void notReturnErrorWhenPersonPersonalCodeIsValidFormat() {
        when(person.getPersonalCode()).thenReturn("080565-15728");
        Optional<ValidationErrorDTO> error = validation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }
}
