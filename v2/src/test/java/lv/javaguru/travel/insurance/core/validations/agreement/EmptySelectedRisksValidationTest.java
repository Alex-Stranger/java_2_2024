package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptySelectedRisksValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptySelectedRisksValidation emptySelectedRisksValidation;

    @Test
    public void returnErrorWhenSelectedRisksIaNull() {
        when(agreement.getSelectedRisks()).thenReturn(null);
        ValidationErrorDTO validationError = new ValidationErrorDTO();
        when(validationErrorFactory.buildError("ERROR_CODE_6")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = emptySelectedRisksValidation.validate(agreement);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void  returnErrorWhenSelectedRisksIsEmpty() {
        when(agreement.getSelectedRisks()).thenReturn(List.of());
        ValidationErrorDTO validationError = new ValidationErrorDTO();
        when(validationErrorFactory.buildError("ERROR_CODE_6")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = emptySelectedRisksValidation.validate(agreement);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void netReturnErrorWhenSelectedRisksIsNotEmpty() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_CANCELLATION", "TRAVEL_BAGGAGE"));
        Optional<ValidationErrorDTO> error = emptySelectedRisksValidation.validate(agreement);
        assertTrue(error.isEmpty());
    }


}
