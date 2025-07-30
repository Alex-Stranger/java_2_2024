package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

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
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (isCountryNotBlank(agreement)) && !existInDatabase(agreement.getCountry())
                ?Optional.of(validationErrorFactory.buildError("ERROR_CODE_13"))
                :Optional.empty();
    }

    private boolean isCountryNotBlank(AgreementDTO agreement) {
        return agreement.getCountry() != null && !agreement.getCountry().isBlank();
    }

    private boolean existInDatabase(String countryIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", countryIc).isPresent();
    }
}
