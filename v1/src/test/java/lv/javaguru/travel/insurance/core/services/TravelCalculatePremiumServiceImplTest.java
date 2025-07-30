package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;

import lv.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @Mock
    private TravelPremiumUnderwriting premiumUnderwriting;

    @Mock
    private TravelCalculatePremiumRequestValidator requestValidator;

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private TravelPremiumCalculationResult premiumCalculationResult;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl premiumService;

    @Test
    public void personFirstName() {
        when(request.getPersonFirstName()).thenReturn("Tom");
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(premiumUnderwriting.calculatePremium(request)).thenReturn(premiumCalculationResult);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertEquals(response.getPersonFirstName(), "Tom");
    }

    @Test
    public void personLastName() {
        when(request.getPersonLastName()).thenReturn("Sawyer");
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(premiumUnderwriting.calculatePremium(request)).thenReturn(premiumCalculationResult);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertEquals(response.getPersonLastName(), "Sawyer");
    }

    @Test
    public void agreementDateFrom() {
        Date dateFrom = new Date();
        when(request.getAgreementDateFrom()).thenReturn(dateFrom);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(premiumUnderwriting.calculatePremium(request)).thenReturn(premiumCalculationResult);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertEquals(response.getAgreementDateFrom(), dateFrom);
    }

    @Test
    public void agreementDateTo() {
        Date dateTo = new Date();
        when(request.getAgreementDateTo()).thenReturn(dateTo);
        when(requestValidator.validate(request)).thenReturn(List.of());
        when(premiumUnderwriting.calculatePremium(request)).thenReturn(premiumCalculationResult);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertEquals(response.getAgreementDateTo(), dateTo);
    }

    @Test
    public void responseWithCorrectAgreementPrice() {
        when(request.getAgreementDateFrom()).thenReturn(createDate("2005.05.15"));
        when(request.getAgreementDateTo()).thenReturn(createDate("2005.05.20"));
        when(requestValidator.validate(request)).thenReturn(List.of());
        TravelPremiumCalculationResult premiumCalculationResult = new TravelPremiumCalculationResult(new BigDecimal(5), null);
        when(premiumUnderwriting.calculatePremium(request)).thenReturn(premiumCalculationResult);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertEquals(response.getAgreementPremium(), new BigDecimal(5));
    }

    @Test
    public void responseWithErrors() {
        List<ValidationError> errors = createValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertTrue(response.hasErrors());
    }

    @Test
    public void responseWithValidationErrors() {
        List<ValidationError> errors = createValidationErrorList();
        when(requestValidator.validate(request)).thenReturn(errors);
        TravelCalculatePremiumResponseV1 response = premiumService.calculatePremium(request);
        assertEquals(errors.size(), 1);
        assertEquals(response.getErrors().get(0).getErrorCode(), "field");
        assertEquals(response.getErrors().get(0).getDescription(), "errorMessage");
    }



    private Date createDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ValidationError> createValidationErrorList() {
        return List.of(new ValidationError("field", "errorMessage"));
    }

}