package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgreementTotalPremiumCalculatorTest {

    @InjectMocks
    private AgreementTotalPremiumCalculator calculator;

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person1;

    @Mock
    private PersonDTO person2;

    @Mock
    private RiskDTO risk1;

    @Mock
    private RiskDTO risk2;

    @Mock
    private RiskDTO risk3;

    @Mock
    private RiskDTO risk4;

    @Test
    void calculateTotalAgreementPremium_ShouldReturnCorrectTotalPremium() {
        when(risk1.getPremium()).thenReturn(BigDecimal.valueOf(100));
        when(risk2.getPremium()).thenReturn(BigDecimal.valueOf(200));
        when(risk3.getPremium()).thenReturn(BigDecimal.valueOf(150));
        when(risk4.getPremium()).thenReturn(BigDecimal.valueOf(50));
        when(person1.getRisks()).thenReturn(List.of(risk1, risk2));
        when(person2.getRisks()).thenReturn(List.of(risk3, risk4));
        when(agreement.getPersons()).thenReturn(List.of(person1, person2));
        BigDecimal totalPremium = calculator.calculate(agreement);
        assertEquals(BigDecimal.valueOf(500), totalPremium);
        verify(agreement, times(1)).getPersons();
        verify(person1, times(1)).getRisks();
        verify(person2, times(1)).getRisks();
        verify(risk1, times(1)).getPremium();
        verify(risk2, times(1)).getPremium();
        verify(risk3, times(1)).getPremium();
        verify(risk4, times(1)).getPremium();
        verifyNoMoreInteractions(agreement, person1, person2, risk1, risk2, risk3, risk4);
    }

    @Test
    void calculateTotalAgreementPremium_ShouldReturnZero_WhenNoPersons() {
        when(agreement.getPersons()).thenReturn(List.of());
        BigDecimal totalPremium = calculator.calculate(agreement);
        assertEquals(BigDecimal.ZERO, totalPremium);
        verify(agreement, times(1)).getPersons();
        verifyNoMoreInteractions(agreement);
    }

    @Test
    void calculateTotalAgreementPremium_ShouldReturnZero_WhenNoRisks() {
        when(person1.getRisks()).thenReturn(List.of());
        when(person2.getRisks()).thenReturn(List.of());
        when(agreement.getPersons()).thenReturn(List.of(person1, person2));
        BigDecimal totalPremium = calculator.calculate(agreement);
        assertEquals(BigDecimal.ZERO, totalPremium);
        verify(agreement, times(1)).getPersons();
        verify(person1, times(1)).getRisks();
        verify(person2, times(1)).getRisks();
        verifyNoMoreInteractions(agreement, person1, person2);
    }
}
