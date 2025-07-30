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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmptyMedicalRiskLimitLevelValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptyMedicalRiskLimitLevelValidation validation;

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsNullAndValidationIsEnabled() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(person.getMedicalRiskLimitLevel()).thenReturn(null);
        ValidationErrorDTO validationError = new ValidationErrorDTO("ERROR_CODE_15", "Medical risk limit level is missing");
        when(validationErrorFactory.buildError("ERROR_CODE_15")).thenReturn(validationError);
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_15", result.get().getErrorCode());
        assertEquals("Medical risk limit level is missing", result.get().getDescription());
    }

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsEmptyAndValidationIsEnabled() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(person.getMedicalRiskLimitLevel()).thenReturn("");
        ValidationErrorDTO validationError = new ValidationErrorDTO("ERROR_CODE_15", "Medical risk limit level is missing");
        when(validationErrorFactory.buildError("ERROR_CODE_15")).thenReturn(validationError);
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_15", result.get().getErrorCode());
        assertEquals("Medical risk limit level is missing", result.get().getDescription());
    }

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsPresent() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(person.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isEmpty());
    }

}
