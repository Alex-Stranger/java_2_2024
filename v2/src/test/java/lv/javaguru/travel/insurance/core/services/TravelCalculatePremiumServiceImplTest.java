package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;

import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    @InjectMocks
    private TravelCalculatePremiumServiceImpl service;

    @Mock
    private TravelAgreementValidator agreementValidator;

    @Mock
    private AgreementPersonsPremiumCalculator agreementPersonsPremiumCalculator;

    @Mock
    private AgreementTotalPremiumCalculator agreementTotalPremiumCalculator;

    @Mock
    private AgreementEntityFactory agreementEntityFactory;

    @Mock
    private AgreementEntity agreementEntity;

    private AgreementDTO agreement;

    private TravelCalculatePremiumCoreCommand command;

    @BeforeEach
    void setUp() {
        agreement = new AgreementDTO();
        agreement.setPersons(Collections.emptyList());
        command = new TravelCalculatePremiumCoreCommand(agreement);
    }

    @Test
    void calculatePremiumReturnsErrorResponse() {
        List<ValidationErrorDTO> validationErrors = List.of(
                new ValidationErrorDTO("fieldName", "errorMessage")
        );
        when(agreementValidator.validate(agreement)).thenReturn(validationErrors);
        TravelCalculatePremiumCoreResult result = service.calculatePremium(command);
        assertNotNull(result);
        assertEquals(validationErrors, result.getErrors());
        verify(agreementValidator, times(1)).validate(agreement);
        verifyNoInteractions(agreementPersonsPremiumCalculator, agreementTotalPremiumCalculator, agreementEntityFactory);
    }

    @Test
    void calculatePremiumWithOnePerson() {
        PersonDTO person = new PersonDTO();
        agreement.setPersons(List.of(person));

        when(agreementValidator.validate(agreement)).thenReturn(Collections.emptyList());

        BigDecimal totalPremium = BigDecimal.valueOf(150.0);
        when(agreementTotalPremiumCalculator.calculate(agreement)).thenReturn(totalPremium);

        UUID uuid = UUID.randomUUID();

        when(agreementEntity.getUuid()).thenReturn(uuid.toString());
        when(agreementEntityFactory.createAgreementEntity(agreement)).thenReturn(agreementEntity);

        TravelCalculatePremiumCoreResult result = service.calculatePremium(command);

        assertNotNull(result);
        assertNull(result.getErrors());
        assertEquals(agreement, result.getAgreement());
        assertEquals(totalPremium, agreement.getAgreementPremium());
        assertEquals(uuid.toString(), agreement.getUuid());

        verify(agreementValidator).validate(agreement);
        verify(agreementPersonsPremiumCalculator).calculateRiskPremiums(agreement);
        verify(agreementTotalPremiumCalculator).calculate(agreement);
        verify(agreementEntityFactory).createAgreementEntity(agreement);
    }

    @Test
    void calculatePremiumWithTwoPersons() {
        PersonDTO person1 = new PersonDTO();
        PersonDTO person2 = new PersonDTO();
        agreement.setPersons(List.of(person1, person2));

        when(agreementValidator.validate(agreement)).thenReturn(Collections.emptyList());

        BigDecimal totalPremium = BigDecimal.valueOf(300.0);
        when(agreementTotalPremiumCalculator.calculate(agreement)).thenReturn(totalPremium);

        UUID uuid = UUID.randomUUID();

        when(agreementEntity.getUuid()).thenReturn(uuid.toString());
        when(agreementEntityFactory.createAgreementEntity(agreement)).thenReturn(agreementEntity);

        TravelCalculatePremiumCoreResult result = service.calculatePremium(command);

        assertNotNull(result);
        assertNull(result.getErrors());
        assertEquals(agreement, result.getAgreement());
        assertEquals(totalPremium, agreement.getAgreementPremium());
        assertEquals(uuid.toString(), agreement.getUuid());

        verify(agreementValidator).validate(agreement);
        verify(agreementPersonsPremiumCalculator).calculateRiskPremiums(agreement);
        verify(agreementTotalPremiumCalculator).calculate(agreement);
        verify(agreementEntityFactory).createAgreementEntity(agreement);
    }
}
