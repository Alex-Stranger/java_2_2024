package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;

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
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptySelectedRisksValidation emptySelectedRisksValidation;

    @Test
    public void returnErrorWhenSelectedRisksIaNull() {
        when(request.getSelectedRisks()).thenReturn(null);
        ValidationError validationError = new ValidationError();
        when(validationErrorFactory.buildError("ERROR_CODE_6")).thenReturn(validationError);
        Optional<ValidationError> error = emptySelectedRisksValidation.validate(request);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void  returnErrorWhenSelectedRisksIsEmpty() {
        when(request.getSelectedRisks()).thenReturn(List.of());
        ValidationError validationError = new ValidationError();
        when(validationErrorFactory.buildError("ERROR_CODE_6")).thenReturn(validationError);
        Optional<ValidationError> error = emptySelectedRisksValidation.validate(request);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    @Test
    public void netReturnErrorWhenSelectedRisksIsNotEmpty() {
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_CANCELLATION", "TRAVEL_BAGGAGE"));
        Optional<ValidationError> error = emptySelectedRisksValidation.validate(request);
        assertTrue(error.isEmpty());
    }


}
