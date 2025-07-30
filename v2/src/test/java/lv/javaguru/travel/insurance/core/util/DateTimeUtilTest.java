package lv.javaguru.travel.insurance.core.util;


import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilTest {

    private DateTimeUtil dateTimeUtil = new DateTimeUtil();

    @Test
    public void daysBetweenDates() {
        Date dateFrom = createDate("2023.01.01");
        Date dateTo = createDate("2023.01.10");
        long numberOfDays = dateTimeUtil.calculateAgreementDaysBetweenDates(dateFrom, dateTo);
        assertEquals(numberOfDays, 9);
    }

    private Date createDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}