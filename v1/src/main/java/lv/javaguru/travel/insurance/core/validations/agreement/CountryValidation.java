package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class CountryValidation extends AgreementFieldValidationImpl {

    @Autowired
    private ClassifierValueRepository classifierValueRepository;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (isCountryNotBlank(request)) && !existInDatabase(request.getCountry())
                ?Optional.of(validationErrorFactory.buildError("ERROR_CODE_13"))
                :Optional.empty();
    }

    private boolean isCountryNotBlank(TravelCalculatePremiumRequestV1 request) {
        return request.getCountry() != null && !request.getCountry().isBlank();
    }

    private boolean existInDatabase(String countryIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", countryIc).isPresent();
    }
}
