package lv.javaguru.travel.insurance.core.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDTOBuilder {

    private String personFirstName;
    private String personLastName;
    private String personalCode;
    private Date personBirthDate;
    private String medicalRiskLimitLevel;
    private BigDecimal travelCost;
    private List<RiskDTO> risks = new ArrayList<>();

    public static PersonDTOBuilder createPerson() {
        return new PersonDTOBuilder();
    }

    public PersonDTO build() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonFirstName(personFirstName);
        personDTO.setPersonLastName(personLastName);
        personDTO.setPersonalCode(personalCode);
        personDTO.setPersonBirthDate(personBirthDate);
        personDTO.setMedicalRiskLimitLevel(medicalRiskLimitLevel);
        personDTO.setTravelCost(travelCost);
        personDTO.setRisks(risks);
        return personDTO;
    }

    public PersonDTOBuilder withPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
        return this;
    }

    public PersonDTOBuilder withPersonLastName(String personLastName) {
        this.personLastName = personLastName;
        return this;
    }

    public PersonDTOBuilder withPersonPersonalCode(String personPersonalCode) {
        this.personalCode = personPersonalCode;
        return this;
    }

    public PersonDTOBuilder withPersonBirthDate(Date personBirthDate) {
        this.personBirthDate = personBirthDate;
        return this;
    }

    public PersonDTOBuilder withMedicalRiskLimitLevel(String medicalRiskLimitLevel) {
        this.medicalRiskLimitLevel = medicalRiskLimitLevel;
        return this;
    }

    public PersonDTOBuilder withTravelCost(BigDecimal travelCost) {
        this.travelCost = travelCost;
        return this;
    }

    public PersonDTOBuilder withRisk(RiskDTO riskDTO) {
        this.risks.add(riskDTO);
        return this;
    }

    public PersonDTOBuilder withRisks(List<RiskDTO> risks) {
        this.risks = risks;
        return this;
    }

}
