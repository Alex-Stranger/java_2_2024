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
class MedicalRiskLimitLevelValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private MedicalRiskLimitLevelValidation validation;

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsInDatabase() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_15000"))
                .thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsNotInDatabase() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("INVALID_LEVEL");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "INVALID_LEVEL"))
                .thenReturn(Optional.empty());
        ValidationError validationError = new ValidationError("ERROR_CODE_14", "Invalid Medical Risk Limit Level!");
        when(validationErrorFactory.buildError("ERROR_CODE_14")).thenReturn(validationError);
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_14", result.get().getErrorCode());
    }

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsNull() {
        when(request.getMedicalRiskLimitLevel()).thenReturn(null);
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isEmpty());
    }

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsEmpty() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("");
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsBlankAndNotInDatabase() {
        when(request.getMedicalRiskLimitLevel()).thenReturn("");
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isEmpty());
    }
}


