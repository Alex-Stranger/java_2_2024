package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
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
class MedicalRiskLimitLevelValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private MedicalRiskLimitLevelValidation validation;

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsInDatabase() {
        when(person.getMedicalRiskLimitLevel()).thenReturn("LEVEL_15000");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_15000"))
                .thenReturn(Optional.of(new ClassifierValue()));
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsNotInDatabase() {
        when(person.getMedicalRiskLimitLevel()).thenReturn("INVALID_LEVEL");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "INVALID_LEVEL"))
                .thenReturn(Optional.empty());
        ValidationErrorDTO validationError = new ValidationErrorDTO("ERROR_CODE_14", "Invalid Medical Risk Limit Level!");
        when(validationErrorFactory.buildError("ERROR_CODE_14")).thenReturn(validationError);
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_14", result.get().getErrorCode());
    }

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsNull() {
        when(person.getMedicalRiskLimitLevel()).thenReturn(null);
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isEmpty());
    }

    @Test
    public void notReturnErrorWhenMedicalRiskLimitLevelIsEmpty() {
        when(person.getMedicalRiskLimitLevel()).thenReturn("");
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isEmpty());
    }

    @Test
    public void returnErrorWhenMedicalRiskLimitLevelIsBlankAndNotInDatabase() {
        when(person.getMedicalRiskLimitLevel()).thenReturn("");
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isEmpty());
    }
}


