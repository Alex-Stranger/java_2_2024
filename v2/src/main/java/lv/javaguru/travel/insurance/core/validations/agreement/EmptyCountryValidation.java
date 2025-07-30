package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyCountryValidation extends AgreementFieldValidationImpl {

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (countryIsNullOrEmpty(agreement))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean countryIsNullOrEmpty(AgreementDTO agreement) {
        return (agreement.getCountry() == null || agreement.getCountry().isBlank());
    }
}
