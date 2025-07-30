package lv.javaguru.travel.insurance.core.validations;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelAgreementFieldValidatorTest {

    @InjectMocks
    private TravelAgreementFieldValidator validator;

    @Mock
    private AgreementDTO agreement;

    @Mock
    private AgreementFieldValidation agreementValidation1;

    @Test
    public void shouldReturnSingleAgreementErrors() {
        ValidationErrorDTO error1 = new ValidationErrorDTO("field1", "Agreement error 1");
        when(agreementValidation1.validate(agreement)).thenReturn(Optional.of(error1));
        when(agreementValidation1.validateList(agreement)).thenReturn(Collections.emptyList());
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(agreementValidation1));
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(1, errors.size());
        assertEquals(error1, errors.get(0));
    }

    @Test
    public void shouldReturnListAgreementErrors() {
        ValidationErrorDTO error1 = new ValidationErrorDTO("field1", "List agreement error 1");
        when(agreementValidation1.validate(agreement)).thenReturn(Optional.empty());
        when(agreementValidation1.validateList(agreement)).thenReturn(List.of(error1));
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(agreementValidation1));
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(1, errors.size());
        assertEquals(error1, errors.get(0));
    }

    @Test
    public void shouldReturnBothSingleAndListAgreementErrors() {
        ValidationErrorDTO singleError = new ValidationErrorDTO("field1", "Agreement error 1");
        ValidationErrorDTO listError = new ValidationErrorDTO("field2", "List agreement error 1");
        when(agreementValidation1.validate(agreement)).thenReturn(Optional.of(singleError));
        when(agreementValidation1.validateList(agreement)).thenReturn(List.of(listError));
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(agreementValidation1));
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(2, errors.size());
        assertEquals(singleError, errors.get(0));
        assertEquals(listError, errors.get(1));
    }

    @Test
    public void shouldReturnEmptyErrorsWhenNoValidationErrorsExist() {
        when(agreementValidation1.validate(agreement)).thenReturn(Optional.empty());
        when(agreementValidation1.validateList(agreement)).thenReturn(Collections.emptyList());
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(agreementValidation1));
        List<ValidationErrorDTO> errors = validator.validate(agreement);
        assertEquals(0, errors.size());
    }
}


