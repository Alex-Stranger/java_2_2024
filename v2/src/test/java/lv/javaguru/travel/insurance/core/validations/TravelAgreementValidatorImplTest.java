package lv.javaguru.travel.insurance.core.validations;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelAgreementValidatorImplTest {

    @InjectMocks
    private TravelAgreementValidatorImpl validator;

    @Mock
    private TravelAgreementFieldValidator agreementFieldValidator;

    @Mock
    private TravelPersonFieldValidator personFieldValidator;

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person1;

    @Mock
    private PersonDTO person2;

    @Test
    public void shouldNotReturnErrors() {
        when(agreementFieldValidator.validate(agreement)).thenReturn(List.of());
        when(personFieldValidator.validate(agreement)).thenReturn(List.of());
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnMixedErrors() {
        ValidationErrorDTO agreementError = new ValidationErrorDTO("field1", "Agreement error");
        ValidationErrorDTO personError = new ValidationErrorDTO("field2", "Person error");
        when(agreementFieldValidator.validate(agreement)).thenReturn(List.of(agreementError));
        when(personFieldValidator.validate(agreement)).thenReturn(List.of(personError));
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(2, errors.size());
        assertTrue(errors.contains(agreementError));
        assertTrue(errors.contains(personError));
    }

    @Test
    public void shouldReturnOnlyAgreementErrors() {
        ValidationErrorDTO agreementError = new ValidationErrorDTO("field1", "Agreement error");
        when(agreementFieldValidator.validate(agreement)).thenReturn(List.of(agreementError));
        when(personFieldValidator.validate(agreement)).thenReturn(List.of());
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(1, errors.size());
        assertTrue(errors.contains(agreementError));
    }

    @Test
    public void shouldReturnOnlyPersonErrors() {
        ValidationErrorDTO personError = new ValidationErrorDTO("field2", "Person error");
        when(agreementFieldValidator.validate(agreement)).thenReturn(List.of());
        when(personFieldValidator.validate(agreement)).thenReturn(List.of(personError));
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(1, errors.size());
        assertTrue(errors.contains(personError));
    }
}





