package lv.javaguru.travel.insurance.core.validations;

import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelCalculatePremiumRequestValidatorImplTest {

    @InjectMocks
    private TravelCalculatePremiumRequestValidatorImpl validator;

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Test
    public void shouldNotReturnErrors() {
        AgreementFieldValidation validation1 = mock(AgreementFieldValidation.class);
        when(validation1.validate(request)).thenReturn(Optional.empty());
        when(validation1.validateList(request)).thenReturn(List.of());
        AgreementFieldValidation validation2 = mock(AgreementFieldValidation.class);
        when(validation2.validate(request)).thenReturn(Optional.empty());
        when(validation2.validateList(request)).thenReturn(List.of());
        PersonFieldValidation validation3 = mock(PersonFieldValidation.class);
        when(validation3.validate(request)).thenReturn(Optional.empty());
        when(validation3.validateList(request)).thenReturn(List.of());
        PersonFieldValidation validation4 = mock(PersonFieldValidation.class);
        when(validation4.validate(request)).thenReturn(Optional.empty());
        when(validation4.validateList(request)).thenReturn(List.of());
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(validation1, validation2));
        ReflectionTestUtils.setField(validator, "personFieldValidations", List.of(validation3, validation4));
        List<ValidationError> errors = validator.validate(request);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void shouldReturnSingleAgreementErrors() {
        AgreementFieldValidation validation1 = mock(AgreementFieldValidation.class);
        when(validation1.validate(request)).thenReturn(Optional.of(new ValidationError("field1", "Agreement error 1")));
        AgreementFieldValidation validation2 = mock(AgreementFieldValidation.class);
        when(validation2.validate(request)).thenReturn(Optional.of(new ValidationError("field2", "Agreement error 2")));
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(validation1, validation2));
        ReflectionTestUtils.setField(validator, "personFieldValidations", List.of());
        List<ValidationError> errors = validator.validate(request);
        assertEquals(2, errors.size());
    }

    @Test
    public void shouldReturnSinglePersonErrors() {
        PersonFieldValidation validation1 = mock(PersonFieldValidation.class);
        when(validation1.validate(request)).thenReturn(Optional.of(new ValidationError("field1", "Person error 1")));
        PersonFieldValidation validation2 = mock(PersonFieldValidation.class);
        when(validation2.validate(request)).thenReturn(Optional.of(new ValidationError("field2", "Person error 2")));
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of());
        ReflectionTestUtils.setField(validator, "personFieldValidations", List.of(validation1, validation2));
        List<ValidationError> errors = validator.validate(request);
        assertEquals(2, errors.size());
    }

    @Test
    public void shouldReturnListAgreementErrors() {
        AgreementFieldValidation validation1 = mock(AgreementFieldValidation.class);
        when(validation1.validate(request)).thenReturn(Optional.empty());
        when(validation1.validateList(request)).thenReturn(List.of(new ValidationError("field1", "List agreement error 1")));
        AgreementFieldValidation validation2 = mock(AgreementFieldValidation.class);
        when(validation2.validate(request)).thenReturn(Optional.empty());
        when(validation2.validateList(request)).thenReturn(List.of(new ValidationError("field2", "List agreement error 2")));
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of(validation1, validation2));
        ReflectionTestUtils.setField(validator, "personFieldValidations", List.of());
        List<ValidationError> errors = validator.validate(request);
        assertEquals(2, errors.size());
    }

    @Test
    public void shouldReturnListPersonErrors() {
        PersonFieldValidation validation1 = mock(PersonFieldValidation.class);
        when(validation1.validate(request)).thenReturn(Optional.empty());
        when(validation1.validateList(request)).thenReturn(List.of(new ValidationError("field1", "List person error 1")));
        PersonFieldValidation validation2 = mock(PersonFieldValidation.class);
        when(validation2.validate(request)).thenReturn(Optional.empty());
        when(validation2.validateList(request)).thenReturn(List.of(new ValidationError("field2", "List person error 2")));
        ReflectionTestUtils.setField(validator, "agreementFieldValidations", List.of());
        ReflectionTestUtils.setField(validator, "personFieldValidations", List.of(validation1, validation2));
        List<ValidationError> errors = validator.validate(request);
        assertEquals(2, errors.size());
    }
}