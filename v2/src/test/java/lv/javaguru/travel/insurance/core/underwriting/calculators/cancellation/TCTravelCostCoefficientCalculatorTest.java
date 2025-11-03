package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import lv.javaguru.travel.insurance.core.repositories.v1.cancellation.TCTravelCostCoefficientRepositoryV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TCTravelCostCoefficientCalculatorTest {

    @Mock
    private TCTravelCostCoefficientRepositoryV1 travelCostCoefficientRepository;

    @InjectMocks
    private TCTravelCostCoefficientCalculator travelCostCoefficientCalculator;

    private PersonDTO person;


    @BeforeEach
    void setUp() {
        person = new PersonDTO();
    }

    @Test
    void shouldReturnCoefficientWhenExists() {
        person.setTravelCost(BigDecimal.valueOf(5000));
        TCTravelCostCoefficient travelCostCoefficient = new TCTravelCostCoefficient();
        travelCostCoefficient.setCoefficient(BigDecimal.valueOf(30.0));
        when(travelCostCoefficientRepository.findCoefficientByTravelCost(BigDecimal.valueOf(5000))).thenReturn(Optional.of(travelCostCoefficient));
        BigDecimal result = travelCostCoefficientCalculator.calculateTravelCost(person);
        assertEquals(0, result.compareTo(BigDecimal.valueOf(30.0)));
        verify(travelCostCoefficientRepository, times(1)).findCoefficientByTravelCost(BigDecimal.valueOf(5000));
    }

    @Test
    void shouldThrowExceptionWhenCoefficientNotFound() {
        person.setTravelCost(BigDecimal.valueOf(5000));
        when(travelCostCoefficientRepository.findCoefficientByTravelCost(BigDecimal.valueOf(5000))).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> travelCostCoefficientCalculator.calculateTravelCost(person));
        assertTrue(exception.getMessage().contains("Travel cost coefficient not found"));
        verify(travelCostCoefficientRepository, times(1)).findCoefficientByTravelCost(BigDecimal.valueOf(5000));
    }
}
