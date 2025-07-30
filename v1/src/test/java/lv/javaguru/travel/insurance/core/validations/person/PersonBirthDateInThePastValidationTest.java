package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.util.DateTimeUtil;

import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonBirthDateInThePastValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonBirthDateInThePastValidation validation;

    @Test
    public void returnErrorWhenPersonBirthDateNotInThePast() {
        when(request.getPersonBirthDate()).thenReturn(createDate("2029.04.01"));
        when(dateTimeUtil.currentDate()).thenReturn(createDate("2022.04.01"));
        ValidationError validationError = new ValidationError();
        when(validationErrorFactory.buildError("ERROR_CODE_12")).thenReturn(validationError);
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isPresent());
        assertEquals(result.get(), validationError);
    }

    @Test
    public void notReturnErrorWhenPersonBirthDateInThePast() {
        when(request.getPersonBirthDate()).thenReturn(createDate("2022.04.01"));
        when(dateTimeUtil.currentDate()).thenReturn(createDate("2029.04.01"));
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isEmpty());
    }

    private Date createDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }




}
