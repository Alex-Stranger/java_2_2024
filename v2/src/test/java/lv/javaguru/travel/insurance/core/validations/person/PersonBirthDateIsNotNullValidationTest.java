package lv.javaguru.travel.insurance.core.validations.person;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonBirthDateIsNotNullValidationTest {

    @Mock
    private AgreementDTO agreement;

    @Mock
    private PersonDTO person;

    @Mock
    private ValidationErrorFactory validationErrorFactory;

    @InjectMocks
    private PersonBirthDateIsNotNullValidation validation;

    @Test
    public void whenPersonBirthDateIsNull() {
        when(person.getPersonBirthDate()).thenReturn(null);
        when(validationErrorFactory.buildError("ERROR_CODE_11")).thenReturn(new ValidationErrorDTO("ERROR_CODE_11", "Person birth date must be provided!"));
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
        assertTrue(result.isPresent());
        assertEquals("ERROR_CODE_11", result.get().getErrorCode());
        assertEquals("Person birth date must be provided!", result.get().getDescription());
    }

    @Test
    public void whenPersonBirthDateIsNotNull() {
        when(person.getPersonBirthDate()).thenReturn(createDate("2022.04.01"));
        Optional<ValidationErrorDTO> result = validation.validate(agreement, person);
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
