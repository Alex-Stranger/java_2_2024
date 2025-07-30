package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptyCountryValidation emptyCountryValidation;

    @Test
    public void notReturnErrorWhenCountryIsNotEmpty() {
        when(agreement.getCountry()).thenReturn("JAPAN");
        Optional<ValidationErrorDTO> result = emptyCountryValidation.validate(agreement);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenCountryIsEmpty() {
        when(agreement.getCountry()).thenReturn("");
        ValidationErrorDTO validationError = new ValidationErrorDTO();
        when(validationErrorFactory.buildError("ERROR_CODE_10")).thenReturn(validationError);
        Optional<ValidationErrorDTO> result = emptyCountryValidation.validate(agreement);
        assertTrue(result.isPresent());
    }

    @Test
    public void returnErrorWhenCountryIsNull() {
        when(agreement.getCountry()).thenReturn(null);
        ValidationErrorDTO validationError = new ValidationErrorDTO();
        when(validationErrorFactory.buildError("ERROR_CODE_10")).thenReturn(validationError);
        Optional<ValidationErrorDTO> result = emptyCountryValidation.validate(agreement);
        assertTrue(result.isPresent());
    }
}
