package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;

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
class CountryValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private CountryValidation countryValidation;

    @Test
    public void notReturnErrorWhenCountryExistsInDatabase() {
        when(request.getCountry()).thenReturn("JAPAN");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "JAPAN")).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationError> result = countryValidation.validate(request);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenCountryNotInDatabase() {
        when(request.getCountry()).thenReturn("UNKNOWN");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "UNKNOWN"))
                .thenReturn(Optional.empty());
        ValidationError validationError = new ValidationError("ERROR_CODE_13", "Country not found in database!");
        when(validationErrorFactory.buildError("ERROR_CODE_13")).thenReturn(validationError);
        Optional<ValidationError> result = countryValidation.validate(request);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_13", result.get().getErrorCode());
        assertEquals("Country not found in database!", result.get().getDescription());
    }

    @Test
    public void notReturnErrorWhenCountryIsInDatabase() {
        when(request.getCountry()).thenReturn("VALID_COUNTRY");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "VALID_COUNTRY")).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationError> result = countryValidation.validate(request);
        assertTrue(result.isEmpty());
    }
}
