package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
class TravelCostValidation extends PersonFieldValidationImpl {

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (travelCancellationRiskSelected(agreement) && travelCostEmpty(person))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_19"))
                : Optional.empty();
    }

    private boolean travelCancellationRiskSelected(AgreementDTO agreement) {
        return agreement.getSelectedRisks() != null && agreement.getSelectedRisks().contains("TRAVEL_CANCELLATION");
    }

    private boolean travelCostEmpty(PersonDTO person) {
        return person.getTravelCost() == null || person.getTravelCost().compareTo(BigDecimal.ZERO) <=0;
    }
}
