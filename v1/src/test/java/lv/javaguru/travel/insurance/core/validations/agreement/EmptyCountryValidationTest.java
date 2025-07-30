package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyCountryValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptyCountryValidation emptyCountryValidation;

    @Test
    public void notReturnErrorWhenCountryIsNotEmpty() {
        when(request.getCountry()).thenReturn("JAPAN");
        Optional<ValidationError> result = emptyCountryValidation.validate(request);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenCountryIsEmpty() {
        when(request.getCountry()).thenReturn("");
        ValidationError validationError = new ValidationError();
        when(validationErrorFactory.buildError("ERROR_CODE_10")).thenReturn(validationError);
        Optional<ValidationError> result = emptyCountryValidation.validate(request);
        assertTrue(result.isPresent());
    }

    @Test
    public void returnErrorWhenCountryIsNull() {
        when(request.getCountry()).thenReturn(null);
        ValidationError validationError = new ValidationError();
        when(validationErrorFactory.buildError("ERROR_CODE_10")).thenReturn(validationError);
        Optional<ValidationError> result = emptyCountryValidation.validate(request);
        assertTrue(result.isPresent());
    }
}
