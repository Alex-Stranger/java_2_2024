package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class AgreementDateToNotInThePastValidation extends AgreementFieldValidationImpl {

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        Date dateTo = request.getAgreementDateTo();
        Date currentDate = dateTimeUtil.currentDate();
        return (dateTo != null && dateTo.before(currentDate))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_4"))
                : Optional.empty();
    }


}
