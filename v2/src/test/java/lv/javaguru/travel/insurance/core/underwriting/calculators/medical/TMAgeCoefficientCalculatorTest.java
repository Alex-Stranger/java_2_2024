package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.TMAgeCoefficient;
import lv.javaguru.travel.insurance.core.repositories.TMAgeCoefficientRepository;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;

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
class TMAgeCoefficientCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private TMAgeCoefficientRepository ageCoefficientRepository;

    @InjectMocks
    private TMAgeCoefficientCalculator ageCoefficientCalculator;

    @Test
    public void returnDefaultValueWhenMedicalRiskAgeCoefficientIsDisabled() {
        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", false);
        PersonDTO person = new PersonDTO();
        BigDecimal result = ageCoefficientCalculator.calculateAgeCoefficient(person);
        assertEquals(BigDecimal.ONE, result);
    }

    @Test
    public void testAgeCoefficientSuccess() {
        LocalDate birthDate = LocalDate.of(1994, 1, 1);
        LocalDate currentDate = LocalDate.of(2024, 1, 1);
        int age = 30;
        BigDecimal expectedAgeCoefficient = BigDecimal.valueOf(1.1);
        PersonDTO person = new PersonDTO();
        person.setPersonBirthDate(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(dateTimeUtil.currentDate()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        TMAgeCoefficient ageCoefficient = new TMAgeCoefficient();
        ageCoefficient.setCoefficient(expectedAgeCoefficient);
        when(ageCoefficientRepository.findByCoefficient(age)).thenReturn(Optional.of(ageCoefficient));
        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", true);
        BigDecimal result = ageCoefficientCalculator.findAgeCoefficient(person);
        assertEquals(expectedAgeCoefficient, result);
    }

    @Test
    public void testAgeCoefficientNotFound() {
        // Настраиваем даты рождения и текущую дату
        LocalDate birthDate = LocalDate.of(1994, 1, 1);
        LocalDate currentDate = LocalDate.of(2024, 1, 1);
        int expectedAge = 30;

        // Создаем тестовый объект PersonDTO
        PersonDTO person = new PersonDTO();
        person.setPersonBirthDate(Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Мокируем методы и поведение зависимостей
        when(dateTimeUtil.currentDate()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(ageCoefficientRepository.findByCoefficient(expectedAge)).thenReturn(Optional.empty());

        // Устанавливаем флаг medicalRiskAgeCoefficientEnabled в true
        ReflectionTestUtils.setField(ageCoefficientCalculator, "medicalRiskAgeCoefficientEnabled", true);

        // Проверяем, что выбрасывается исключение при отсутствии коэффициента
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ageCoefficientCalculator.findAgeCoefficient(person));

        assertEquals("Age coefficient not found: " + expectedAge, exception.getMessage());
    }


}
