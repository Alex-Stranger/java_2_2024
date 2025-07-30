package lv.javaguru.travel.insurance.core.repositories;

import lv.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MedicalRiskLimitLevelRepositoryTest {

    @Autowired
    private MedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Test
    public void medicalRiskLimitLevelIsSpecified() {
        Optional<MedicalRiskLimitLevel> result = medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc("LEVEL_15000");
        assertTrue(result.isPresent());
        assertEquals(result.get().getMedicalRiskLimitLevelIc(), "LEVEL_15000");
    }

    @Test
    public void medicalRiskLimitLevelNotSpecified() {
        Optional<MedicalRiskLimitLevel> result = medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(null);
        assertTrue(result.isEmpty());
    }
}
