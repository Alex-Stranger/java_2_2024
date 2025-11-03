package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import lv.javaguru.travel.insurance.core.repositories.v1.ClassifierValueRepositoryV1;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private ClassifierValueRepositoryV1 classifierValueRepository;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private SelectedRisksValidation selectedRisksValidation;

    @Test
    public void returnErrorWhenSelectedRisksIsNull() {
        when(agreement.getSelectedRisks()).thenReturn(null);
        assertTrue(selectedRisksValidation.validateList(agreement).isEmpty());
        verifyNoInteractions(classifierValueRepository, validationErrorFactory);
    }

    @Test
    public void validateListWithExistingRisks() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("RISK_IC_1", "RISK_IC_2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_1")).thenReturn(Optional.of(new ClassifierValue()));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_2")).thenReturn(Optional.of(new ClassifierValue()));
        List<ValidationErrorDTO> result = selectedRisksValidation.validateList(agreement);
        assertTrue(result.isEmpty());
    }

    @Test
    public void validateListWithNonExistingRisks() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("RISK_IC_1", "RISK_IC_2"));
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_1")).thenReturn(Optional.empty());
        when(classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", "RISK_IC_2")).thenReturn(Optional.empty());
        ValidationErrorDTO error = new ValidationErrorDTO();
        when(validationErrorFactory.buildError(eq("ERROR_CODE_9"), anyList())).thenReturn(error);
        List<ValidationErrorDTO> result = selectedRisksValidation.validateList(agreement);
        assertEquals(2, result.size());
    }


}
