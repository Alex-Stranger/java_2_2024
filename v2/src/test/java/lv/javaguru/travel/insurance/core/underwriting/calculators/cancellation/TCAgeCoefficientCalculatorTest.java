package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;

import lv.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.v1.cancellation.TCAgeCoefficientRepositoryV1;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TCAgeCoefficientCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private TCAgeCoefficientRepositoryV1 ageCoefficientRepository;

    @InjectMocks
    private TCAgeCoefficientCalculator ageCoefficientCalculator;

    @Test
    public void ageCoefficientSuccess() {
        LocalDate birthDate = LocalDate.of(1994, 1, 1);
        LocalDate currentDate = LocalDate.of(2025, 7, 1);
        int age = 31;
        BigDecimal expectedAgeCoefficient = BigDecimal.valueOf(20);
        PersonDTO person = new PersonDTO();
        person.setPersonBirthDate(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(dateTimeUtil.currentDate()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        TCAgeCoefficient ageCoefficient = new TCAgeCoefficient();
        ageCoefficient.setCoefficient(expectedAgeCoefficient);
        when(ageCoefficientRepository.findCoefficientByAge(age)).thenReturn(Optional.of(ageCoefficient));
        BigDecimal result = ageCoefficientCalculator.ageCoefficient(person);
        assertEquals(expectedAgeCoefficient, result);
    }

    @Test
    public void ageCoefficientNotFound() {
        LocalDate birthDate = LocalDate.of(1994, 1, 1);
        LocalDate currentDate = LocalDate.of(2025, 7, 1);
        int age = 31;
        PersonDTO person = new PersonDTO();
        person.setPersonBirthDate(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(dateTimeUtil.currentDate()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(ageCoefficientRepository.findCoefficientByAge(age)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ageCoefficientCalculator.ageCoefficient(person));

        assertEquals("Age coefficient not found: " + age, exception.getMessage());
    }



}
