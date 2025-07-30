package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.domain.AgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeCoefficientCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private AgeCoefficientRepository ageCoefficientRepository;

    @InjectMocks
    private AgeCoefficientCalculator ageCoefficientCalculator;

    @Test
    public void returnDefaultValueWhenMedicalRiskAgeCoefficientIsDisabled() {
        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", false);
        TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();
        BigDecimal result = ageCoefficientCalculator.calculateAgeCoefficient(request);
        assertEquals(BigDecimal.ONE, result);
    }

    @Test
    public void testAgeCoefficientSuccess() {
        LocalDate birthDate = LocalDate.of(1994, 01, 01);
        LocalDate currentDate = LocalDate.of(2024, 01, 01);
        int age = 30;
        BigDecimal expectedAgeCoefficient = BigDecimal.valueOf(1.1);
        TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();
        request.setPersonBirthDate(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(dateTimeUtil.currentDate()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        AgeCoefficient ageCoefficient = new AgeCoefficient();
        ageCoefficient.setCoefficient(expectedAgeCoefficient);
        when(ageCoefficientRepository.findByCoefficient(age)).thenReturn(Optional.of(ageCoefficient));
        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", true);
        BigDecimal result = ageCoefficientCalculator.findAgeCoefficient(request);
        assertEquals(expectedAgeCoefficient, result);
    }

    @Test
    public void testAgeCoefficientNotFound() {
        LocalDate birthDate = LocalDate.of(1994, 1, 1);
        LocalDate currentDate = LocalDate.of(2024, 1, 1);
        int expectedAge = 30;
        TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();
        request.setPersonBirthDate(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(dateTimeUtil.currentDate()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(ageCoefficientRepository.findByCoefficient(expectedAge)).thenReturn(Optional.empty());
        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", true);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ageCoefficientCalculator.findAgeCoefficient(request));
        assertEquals("Age coefficient not found: " + expectedAge, exception.getMessage());
    }


}
