package lv.javaguru.travel.insurance.core.repositories;

import lv.javaguru.travel.insurance.core.domain.TMMedicalRiskLimitLevel;
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
class TMMedicalRiskLimitLevelRepositoryTest {

    @Autowired
    private TMMedicalRiskLimitLevelRepository medicalRiskLimitLevelRepository;

    @Test
    public void medicalRiskLimitLevelIsSpecified() {
        Optional<TMMedicalRiskLimitLevel> result = medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc("LEVEL_15000");
        assertTrue(result.isPresent());
        assertEquals(result.get().getMedicalRiskLimitLevelIc(), "LEVEL_15000");
    }

    @Test
    public void medicalRiskLimitLevelNotSpecified() {
        Optional<TMMedicalRiskLimitLevel> result = medicalRiskLimitLevelRepository.findByMedicalRiskLimitLevelIc(null);
        assertTrue(result.isEmpty());
    }
}
