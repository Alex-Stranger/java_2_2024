package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import lv.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CountryDefaultDayRateCalculator {

    @Autowired
    private CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    public BigDecimal findCountryDefaultDayRate(TravelCalculatePremiumRequestV1 request) {
        String countryIc = request.getCountry();
        return countryDefaultDayRateRepository.findByCountryIc(countryIc)
                .map(CountryDefaultDayRate:: getDefaultDayRate)
                .orElseThrow(() -> new RuntimeException("Country not found: " + countryIc));
    }
}
