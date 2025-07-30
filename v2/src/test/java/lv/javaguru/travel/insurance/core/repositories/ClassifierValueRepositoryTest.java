package lv.javaguru.travel.insurance.core.repositories;

import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ClassifierValueRepositoryTest {

    @Autowired
    private ClassifierValueRepository classifierValueRepository;

    @Test
    public void injectRepositoryAreNotNull() {
        assertNotNull(classifierValueRepository);
    }

    @Test
    public void shouldFind_RiskType_TRAVEL_MEDICAL() {
        assertClassifierValue("RISK_TYPE", "TRAVEL_MEDICAL", true);
    }

    @Test
    public void shouldFind_RiskType_TRAVEL_CANCELLATION() {
        assertClassifierValue("RISK_TYPE", "TRAVEL_CANCELLATION", true);
    }

    @Test
    public void shouldFind_RiskType_TRAVEL_LOSS_BAGGAGE() {
        assertClassifierValue("RISK_TYPE", "TRAVEL_LOSS_BAGGAGE", true);
    }

    @Test
    public void shouldFind_RiskType_TRAVEL_THIRD_PARTY_LIABILITY() {
        assertClassifierValue("RISK_TYPE", "TRAVEL_THIRD_PARTY_LIABILITY", true);
    }

    @Test
    public void shouldFind_RiskType_TRAVEL_EVACUATION() {
        assertClassifierValue("RISK_TYPE", "TRAVEL_EVACUATION", true);
    }

    @Test
    public void shouldFind_RiskType_TRAVEL_SPORT_ACTIVITIES() {
        assertClassifierValue("RISK_TYPE", "TRAVEL_SPORT_ACTIVITIES", true);
    }

    @Test
    public void shouldNotFind_RiskType_FAKE() {
        assertClassifierValue("RISK_TYPE", "FAKE", false);
    }

    private void assertClassifierValue(String classifierTitle, String ic, boolean shouldExist) {
        Optional<ClassifierValue> valueOpt = classifierValueRepository.findByClassifierTitleAndIc(classifierTitle, ic);
        if (shouldExist) {
            assertTrue(valueOpt.isPresent());
            assertEquals(valueOpt.get().getIc(), ic);
            assertEquals(valueOpt.get().getClassifier().getTitle(), classifierTitle);
        } else {
            assertTrue(valueOpt.isEmpty());
        }
    }
}
