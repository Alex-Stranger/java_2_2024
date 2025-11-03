package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.repositories.v1.TMCountryDefaultDayRateRepositoryV1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TMCountryDefaultDayRateCalculator {

    @Autowired
    private TMCountryDefaultDayRateRepositoryV1 countryDefaultDayRateRepository;

    public BigDecimal findCountryDefaultDayRate(AgreementDTO agreement) {
        String countryIc = agreement.getCountry();
        return countryDefaultDayRateRepository.findByCountryIc(countryIc)
                .map(TMCountryDefaultDayRate:: getDefaultDayRate)
                .orElseThrow(() -> new RuntimeException("Country not found: " + countryIc));
    }
}
