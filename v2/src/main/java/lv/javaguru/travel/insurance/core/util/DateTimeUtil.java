package lv.javaguru.travel.insurance.core.util;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DateTimeUtil {

    public long calculateAgreementDaysBetweenDates(Date date1, Date date2) {
        long difference = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

    public Date currentDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Riga"))
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return Date.from(zonedDateTime.toInstant());
    }

}
