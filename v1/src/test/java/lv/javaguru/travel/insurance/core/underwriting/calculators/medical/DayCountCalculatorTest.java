package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DayCountCalculatorTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @InjectMocks
    private DayCountCalculator dayCountCalculator;

    @BeforeEach
    void setUp() {

        when(request.getAgreementDateFrom()).thenReturn(createDate(2024, 0, 5));
        when(request.getAgreementDateTo()).thenReturn(createDate(2024, 0, 15));
    }

    @Test
    public void testCalculateDayCount() {
        BigDecimal daysCount = BigDecimal.valueOf(10);
        when(dateTimeUtil.calculateAgreementDaysBetweenDates(request.getAgreementDateFrom(), request.getAgreementDateTo()))
                .thenReturn(daysCount.longValue());
        BigDecimal result = dayCountCalculator.calculateDayCount(request);
        assertEquals(BigDecimal.valueOf(daysCount.longValue()), result);
    }

    private Date createDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month + 1, day)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

