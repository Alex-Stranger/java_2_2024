package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class DayCountCalculator {

    @Autowired
    private DateTimeUtil dateTimeUtil;

    public BigDecimal calculateDayCount(TravelCalculatePremiumRequestV1 request) {
        long differenceBetweenDays = dateTimeUtil.calculateAgreementDaysBetweenDates(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(differenceBetweenDays);
    }
}
