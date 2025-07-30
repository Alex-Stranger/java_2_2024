package lv.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;

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
    private AgreementDTO agreement;
    @InjectMocks
    private DayCountCalculator dayCountCalculator;

    @BeforeEach
    void setUp() {

        when(agreement.getAgreementDateFrom()).thenReturn(createDate(2024, 0, 5));
        when(agreement.getAgreementDateTo()).thenReturn(createDate(2024, 0, 15));
    }

    @Test
    public void testCalculateDayCount() {
        BigDecimal daysCount = BigDecimal.valueOf(10);
        when(dateTimeUtil.calculateAgreementDaysBetweenDates(agreement.getAgreementDateFrom(), agreement.getAgreementDateTo()))
                .thenReturn(daysCount.longValue());
        BigDecimal result = dayCountCalculator.calculateDayCount(agreement);
        assertEquals(BigDecimal.valueOf(daysCount.longValue()), result);
    }

    private Date createDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month + 1, day)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

