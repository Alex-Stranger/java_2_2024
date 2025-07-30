package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
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
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private EmptyMedicalRiskLimitLevelValidation validation;

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsNullAndValidationIsEnabled() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn(null);
        ValidationError validationError = new ValidationError("ERROR_CODE_15", "Medical risk limit level is missing");
        when(validationErrorFactory.buildError("ERROR_CODE_15")).thenReturn(validationError);
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_15", result.get().getErrorCode());
        assertEquals("Medical risk limit level is missing", result.get().getDescription());
    }

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsEmptyAndValidationIsEnabled() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn("");
        ValidationError validationError = new ValidationError("ERROR_CODE_15", "Medical risk limit level is missing");
        when(validationErrorFactory.buildError("ERROR_CODE_15")).thenReturn(validationError);
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_15", result.get().getErrorCode());
        assertEquals("Medical risk limit level is missing", result.get().getDescription());
    }

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsPresent() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", true);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isEmpty());
    }

}
