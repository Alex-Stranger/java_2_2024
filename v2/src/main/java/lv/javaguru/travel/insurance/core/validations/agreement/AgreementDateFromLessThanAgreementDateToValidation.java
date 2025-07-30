package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class AgreementDateFromLessThanAgreementDateToValidation extends AgreementFieldValidationImpl {

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        Date dateFrom = agreement.getAgreementDateFrom();
        Date dateTo = agreement.getAgreementDateTo();
        return (dateFrom != null && dateTo != null && (dateFrom.after(dateTo)))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_1"))
                : Optional.empty();
    }


}
