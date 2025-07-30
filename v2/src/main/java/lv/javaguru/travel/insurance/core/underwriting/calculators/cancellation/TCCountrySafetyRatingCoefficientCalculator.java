package lv.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.cancellation.TCCountrySafetyRatingCoefficient;
import lv.javaguru.travel.insurance.core.repositories.cancellation.TCCountrySafetyRatingCoefficientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TCCountrySafetyRatingCoefficientCalculator {

    @Autowired
    private TCCountrySafetyRatingCoefficientRepository countrySafetyRatingCoefficientRepository;

    public BigDecimal findCountrySafetyRatingCoefficient(AgreementDTO agreement) {
        String countryIc = agreement.getCountry();
        return countrySafetyRatingCoefficientRepository.findCoefficientByCountryIc(countryIc)
                .map(TCCountrySafetyRatingCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Country safety rating coefficient not found: " + countryIc));

    }
}
