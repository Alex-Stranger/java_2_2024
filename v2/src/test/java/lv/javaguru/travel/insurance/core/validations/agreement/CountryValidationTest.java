package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
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
class CountryValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private CountryValidation countryValidation;

    @Test
    public void notReturnErrorWhenCountryExistsInDatabase() {
        when(agreement.getCountry()).thenReturn("JAPAN");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "JAPAN")).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationErrorDTO> result = countryValidation.validate(agreement);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenCountryNotInDatabase() {
        when(agreement.getCountry()).thenReturn("UNKNOWN");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "UNKNOWN"))
                .thenReturn(Optional.empty());
        ValidationErrorDTO validationError = new ValidationErrorDTO("ERROR_CODE_13", "Country not found in database!");
        when(validationErrorFactory.buildError("ERROR_CODE_13")).thenReturn(validationError);
        Optional<ValidationErrorDTO> result = countryValidation.validate(agreement);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_13", result.get().getErrorCode());
        assertEquals("Country not found in database!", result.get().getDescription());
    }

    @Test
    public void notReturnErrorWhenCountryIsInDatabase() {
        when(agreement.getCountry()).thenReturn("VALID_COUNTRY");
        when(classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", "VALID_COUNTRY")).thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationErrorDTO> result = countryValidation.validate(agreement);
        assertTrue(result.isEmpty());
    }
}
