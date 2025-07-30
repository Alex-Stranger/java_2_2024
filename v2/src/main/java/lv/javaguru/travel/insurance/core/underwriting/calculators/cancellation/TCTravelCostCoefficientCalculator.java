package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCTravelCostCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCTravelCostCoefficientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TCTravelCostCoefficientCalculator {

    @Autowired
    private TCTravelCostCoefficientRepository travelCostCoefficientRepository;

    BigDecimal calculateTravelCost(PersonDTO person) {
        return travelCostCoefficientRepository.findCoefficientByTravelCost(person.getTravelCost())
                .map(TCTravelCostCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Travel cost coefficient not found : " + person.getTravelCost()));
    }
}

