package lv.javaguru.travel.insurance.core.validations.person;

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
class PersonBirthDateIsNotNullValidationTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonBirthDateIsNotNullValidation validation;

    @Test
    public void whenPersonBirthDateIsNull() {
        when(request.getPersonBirthDate()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_11")).thenReturn(new ValidationError("ERROR_CODE_11", "Person birth date must be provided!"));
        Optional<ValidationError> result = validation.validate(request);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_11", result.get().getErrorCode());
        assertEquals("Person birth date must be provided!", result.get().getDescription());
    }

    @Test
    public void whenPersonBirthDateIsNotNull() {
        when(request.getPersonBirthDate()).thenReturn(createDate("2022.04.01"));
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
