package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCostValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private TravelCostValidation travelCostValidation;

    @Test
    public void shouldReturnErrorWhenTravelCancellationRiskSelectedAndTravelCostIsNull() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_CANCELLATION"));
        when(person.getTravelCost()).thenReturn(null);
        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO("ERROR_CODE_19", "Field travelCost must be filled in if TRAVEL_CANCELLATION risk is specified!");
        when(validationErrorFactory.buildError("ERROR_CODE_19")).thenReturn(validationErrorDTO);
        Optional<ValidationErrorDTO> error = travelCostValidation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals("ERROR_CODE_19", error.get().getErrorCode());
    }

    @Test
    public void shouldReturnErrorWhenTravelCancellationRiskSelectedAndTravelCostIsZero() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_CANCELLATION"));
        when(person.getTravelCost()).thenReturn(BigDecimal.ZERO);
        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO("ERROR_CODE_19", "Field travelCost must be filled in if TRAVEL_CANCELLATION risk is specified!");
        when(validationErrorFactory.buildError("ERROR_CODE_19")).thenReturn(validationErrorDTO);
        Optional<ValidationErrorDTO> error = travelCostValidation.validate(agreement, person);
        assertTrue(error.isPresent());
        assertEquals("ERROR_CODE_19", error.get().getErrorCode());
    }

    @Test
    public void shouldNotReturnErrorWhenTravelCancellationRiskSelected() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL", "TRAVEL_LOSS_BAGGAGE"));
        Optional<ValidationErrorDTO> error = travelCostValidation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

    @Test
    public void shouldNotReturnErrorWhenTravelCancellationRiskIsNull() {
        when(agreement.getSelectedRisks()).thenReturn(null);
        Optional<ValidationErrorDTO> error = travelCostValidation.validate(agreement, person);
        assertTrue(error.isEmpty());
    }

}
