package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class AgreementPersonsPremiumCalculator {

    @Autowired
    private TravelPremiumUnderwriting premiumUnderwriting;

    List<PersonDTO> calculateRiskPremiums(AgreementDTO agreement) {
        return agreement.getPersons().stream()
                .map(person -> {
                    TravelPremiumCalculationResult result = premiumUnderwriting.calculatePremium(agreement, person);
                    person.setRisks(result.getRisks());
                    return person;
                })
                .collect(Collectors.toList());
    }
}
