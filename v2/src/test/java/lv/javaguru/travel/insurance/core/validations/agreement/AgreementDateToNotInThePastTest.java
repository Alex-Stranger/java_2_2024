package lv.javaguru.travel.insurance.core.validations.agreement;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementDateToNotInThePastTest {

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private AgreementDateToNotInThePastValidation validation;

    @Test
    public void returnErrorWhenAgreementDateToIsInThePast() {
        AgreementDTO agreement = mock(AgreementDTO.class);
        when(agreement.getAgreementDateTo()).thenReturn(createDate("2020.01.10"));
        when(dateTimeUtil.currentDate()).thenReturn(createDate("2023.01.01"));
        ValidationErrorDTO validationError = mock(ValidationErrorDTO.class);
        when(validationErrorFactory.buildError("ERROR_CODE_4")).thenReturn(validationError);
        Optional<ValidationErrorDTO> error = validation.validate(agreement);
        assertTrue(error.isPresent());
        assertEquals(error.get(), validationError);
    }

    private Date createDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
