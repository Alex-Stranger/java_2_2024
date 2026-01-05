package lv.javaguru.travel.insurance.core.validations.integration;

import lv.javaguru.travel.insurance.core.api.dto.*;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AgreementDateFromValidationIntegrationTest {

    @Autowired
    private TravelAgreementValidator validator;

    @Test
    public void shouldReturnErrorWhenDateFromIsNull() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Tom");
        person.setPersonLastName("Sawyer");
        person.setPersonalCode("080565-15728");
        person.setPersonBirthDate(createDate("2022.04.01"));
        person.setMedicalRiskLimitLevel("LEVEL_15000");

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(null)
                .agreementDateTo(createDate("2026.12.29"))
                .country("JAPAN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_3");
        assertEquals(errors.get(0).getDescription(), "Field agreementDateFrom must not be empty!");
    }

    @Test
    public void shouldReturnErrorWhenDateToIsEmpty() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Tom");
        person.setPersonLastName("Sawyer");
        person.setPersonalCode("080565-15728");
        person.setPersonBirthDate(createDate("2022.04.01"));
        person.setMedicalRiskLimitLevel("LEVEL_15000");

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(createDate("2026.10.29"))
                .agreementDateTo(createDate(""))
                .country("JAPAN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_5");
        assertEquals(errors.get(0).getDescription(), "Field agreementDateTo must not be empty!");
    }

    @Test
    public void shouldReturnErrorWhenAgreementDateFromGreaterThanAgreementDateTo() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Tom");
        person.setPersonLastName("Sawyer");
        person.setPersonalCode("080565-15728");
        person.setPersonBirthDate(createDate("2022.04.01"));
        person.setMedicalRiskLimitLevel("LEVEL_15000");

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(createDate("2030.05.05"))
                .agreementDateTo(createDate("2025.05.10"))
                .country("JAPAN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(errors.size(), 2);
        assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_1");
        assertEquals(errors.get(0).getDescription(), "Field agreementDateFrom must be less than agreementDateTo!");
        assertEquals(errors.get(1).getErrorCode(), "ERROR_CODE_4");
        assertEquals(errors.get(1).getDescription(), "Field agreementDateTo should not be in the past!");
    }

    @Test
    public void shouldReturnErrorWhenDateFromIsInThePast() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Tom");
        person.setPersonLastName("Sawyer");
        person.setPersonalCode("080565-15728");
        person.setPersonBirthDate(createDate("1990.04.01"));
        person.setMedicalRiskLimitLevel("LEVEL_15000");

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(createDate("2000.05.20"))
                .agreementDateTo(createDate("2026.11.10"))
                .country("JAPAN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_2");
        assertEquals(errors.get(0).getDescription(), "Field agreementDateFrom should not be in the past!");
    }

    @Test
    public void shouldReturnErrorWhenCountryIsNull() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Tom");
        person.setPersonLastName("Sawyer");
        person.setPersonalCode("080565-15728");
        person.setPersonBirthDate(createDate("1990.04.01"));
        person.setMedicalRiskLimitLevel("LEVEL_15000");

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(createDate("2026.05.25"))
                .agreementDateTo(createDate("2026.12.29"))
                .country(null)
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_10");
        assertEquals(errors.get(0).getDescription(), "Field country must not be empty!");

        }

        @Test
        public void shouldReturnErrorWhenPersonFirstNameIsEmpty() {
        PersonDTO person = PersonDTO.builder()
                .personFirstName("")
                .personLastName("Sawyer")
                .personalCode("080565-15728")
                .personBirthDate(createDate("2022.04.01"))
                .medicalRiskLimitLevel("LEVEL_15000")
                .risks(List.of(RiskDTO.builder()
                        .riskIc("TRAVEL_MEDICAL")
                        .premium(BigDecimal.valueOf(200))
                        .build()))
                .build();

            AgreementDTO agreement = AgreementDTO.builder()
                    .agreementDateFrom(createDate("2026.05.25"))
                    .agreementDateTo(createDate("2026.12.29"))
                    .country("JAPAN")
                    .selectedRisks(List.of("TRAVEL_MEDICAL"))
                    .persons(List.of(person))
                    .build();

            List<ValidationErrorDTO> errors = validator.validate(agreement);

            assertEquals(errors.size(), 1);
            assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_7");
            assertEquals(errors.get(0).getDescription(), "Field personFirstName must not be empty!");
        }

    @Test
    public void shouldReturnErrorWhenPersonalCodeIsEmpty() {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName("Tom");
        person.setPersonLastName("Sawyer");
        person.setPersonalCode("");
        person.setPersonBirthDate(createDate("2022.04.01"));
        person.setMedicalRiskLimitLevel("LEVEL_15000");

        AgreementDTO agreement = AgreementDTO.builder()
                .agreementDateFrom(createDate("2026.05.25"))
                .agreementDateTo(createDate("2026.12.29"))
                .country("JAPAN")
                .selectedRisks(List.of("TRAVEL_MEDICAL"))
                .persons(List.of(person))
                .build();

        List<ValidationErrorDTO> errors = validator.validate(agreement);

        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).getErrorCode(), "ERROR_CODE_16");
        assertEquals(errors.get(0).getDescription(), "Field personalCode must not be empty!");

    }

    private Date createDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy.MM.dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
