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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelectedRisksValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ClassifierValueRepository classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private SelectedRisksValidation selectedRisksValidation;

    @Test
    public void returnErrorWhenSelectedRisksIsNull() {
        when(request.getSelectedRisks()).thenReturn(null);
        assertTrue(selectedRisksValidation.validateList(request).isEmpty());
        verifyNoInteractions(classifierValueRepository, validationErrorFactory);
    }

    @Test
    public void validateListWithExistingRisks() {
        when(request.getSelectedRisks()).thenReturn(List.of("RISK_IC_1", "RISK_IC_2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_1")).thenReturn(Optional.of(new ClassifierValue()));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_2")).thenReturn(Optional.of(new ClassifierValue()));
        List<ValidationError> result = selectedRisksValidation.validateList(request);
        assertTrue(result.isEmpty());
    }

    @Test
    public void validateListWithNonExistingRisks() {
        when(request.getSelectedRisks()).thenReturn(List.of("RISK_IC_1", "RISK_IC_2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_1")).thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_2")).thenReturn(Optional.empty());
        ValidationError error = new ValidationError();
        when(validationErrorFactory.buildError(eq("ERROR_CODE_9"), anyList())).thenReturn(error);
        List<ValidationError> result = selectedRisksValidation.validateList(request);
        assertEquals(2, result.size());
    }


}
