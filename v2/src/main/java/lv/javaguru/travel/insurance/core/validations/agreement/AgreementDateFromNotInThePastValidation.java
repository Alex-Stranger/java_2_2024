package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class AgreementDateFromNotInThePastValidation extends AgreementFieldValidationImpl {

    @Autowired
    private DateTimeUtil dateTimeUtil;
    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        Date dateFrom = agreement.getAgreementDateFrom();
        Date currentDate = dateTimeUtil.currentDate();
        return (dateFrom != null && dateFrom.before(currentDate))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_2"))
                : Optional.empty();
    }
}
