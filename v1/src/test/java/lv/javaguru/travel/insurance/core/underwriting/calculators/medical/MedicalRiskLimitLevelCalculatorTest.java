package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import lv.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

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
class MedicalRiskLimitLevelCalculatorTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @InjectMocks
    private MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Test
    public void returnDefaultValueWhenMedicalRiskLimitLevelIsDisabled() {
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", false);
        BigDecimal result = medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(request);
        assertEquals(BigDecimal.ONE, result);
    }

    @Test
    public void testMedicalRiskLimitLevelSuccess() {
        String medicalRiskLimitLevel = "LEVEL_15000";
        when(request.getMedicalRiskLimitLevel()).thenReturn(medicalRiskLimitLevel);
        MedicalRiskLimitLevel riskLimitLevel = new MedicalRiskLimitLevel();
        riskLimitLevel.setCoefficient(new BigDecimal(1.2));
        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(medicalRiskLimitLevel))
                .thenReturn(Optional.of(riskLimitLevel));
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
        BigDecimal result = medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(request);
        assertEquals(new BigDecimal(1.2), result);
    }

    @Test
    public void medicalRiskLimitLevelNotFound() {
        String medicalRiskLimitLevel = "INVALID_LEVEL";
        when(request.getMedicalRiskLimitLevel()).thenReturn(medicalRiskLimitLevel);;
        request.setMedicalRiskLimitLevel("INVALID_LEVEL");
        when(medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc("INVALID_LEVEL")).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(medicalRiskLimitLevelCalculator, "medicalRiskLimitLevelEnabled", true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> medicalRiskLimitLevelCalculator.calculateMedicalRiskLimitLevel(request));
        assertEquals("Medical risk limit level not found: " + medicalRiskLimitLevel, exception.getMessage());
    }


}
