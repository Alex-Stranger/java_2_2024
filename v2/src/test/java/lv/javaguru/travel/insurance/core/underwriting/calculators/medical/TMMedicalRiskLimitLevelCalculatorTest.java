package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.TMMedicalRiskLimitLevel;
import lv.javaguru.travel.insurance.core.repositories.TMMedicalRiskLimitLevelRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TMMedicalRiskLimitLevelCalculatorTest {

    @Mock
    private PersonDTO person;

    @Mock
    private TMMedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @InjectMocks
    private TMMedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Test
    public void returnDefaultValueWhenMedicalRiskLimitLevelIsDisabled() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", false);
        BigDecimal result = medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(person);
        assertEquals(BigDecimal.ONE, result);
    }

    @Test
    public void testMedicalRiskLimitLevelSuccess() {
        String medicalRiskLimitLevel = "LEVEL_15000";
        when(person.getMedicalRiskLimitLevel()).thenReturn(medicalRiskLimitLevel);
        TMMedicalRiskLimitLevel riskLimitLevel = new TMMedicalRiskLimitLevel();
        riskLimitLevel.setCoefficient(new BigDecimal(1.2));
        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(medicalRiskLimitLevel))
                .thenReturn(Optional.of(riskLimitLevel));
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
        BigDecimal result = medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(person);
        assertEquals(new BigDecimal(1.2), result);
    }

    @Test
    public void medicalRiskLimitLevelNotFound() {
        String medicalRiskLimitLevel = "INVALID_LEVEL";
        when(person.getMedicalRiskLimitLevel()).thenReturn(medicalRiskLimitLevel);;
        person.setMedicalRiskLimitLevel("INVALID_LEVEL");
        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc("INVALID_LEVEL")).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(person));
        assertEquals("Medical risk limit level not found: " + medicalRiskLimitLevel, exception.getMessage());
    }
}
