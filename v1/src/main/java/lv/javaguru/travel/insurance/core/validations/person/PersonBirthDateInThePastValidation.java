package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.util.DateTimeUtil;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class PersonBirthDateInThePastValidation extends PersonFieldValidationImpl {

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        Date personBirthDate = request.getPersonBirthDate();
        Date currentDate = dateTimeUtil.currentDate();
        return (personBirthDate != null && personBirthDate.after(currentDate))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_12"))
                : Optional.empty();
    }
}
