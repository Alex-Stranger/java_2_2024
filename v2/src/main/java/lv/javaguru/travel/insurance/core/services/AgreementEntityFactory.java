package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.PersonDTO;
import lv.javaguru.travel.insurance.core.domain.entities.*;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementPersonEntityRepository;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementPersonRisksEntityRepository;
import lv.javaguru.travel.insurance.core.repositories.entities.SelectedRisksEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
class AgreementEntityFactory {

    @Autowired
    private AgreementEntityRepository agreementEntityRepository;

    @Autowired
    private PersonEntityFactory personEntityFactory;

    @Autowired
    private SelectedRisksEntityRepository selectedRisksEntityRepository;

    @Autowired
    private AgreementPersonEntityRepository agreementPersonEntityRepository;

    @Autowired
    private AgreementPersonRisksEntityRepository agreementPersonRisksEntityRepository;

    public AgreementEntity createAgreementEntity(AgreementDTO agreement) {
        AgreementEntity agreementEntity = saveAgreement(agreement);
        saveAllSelectedRisks(agreement, agreementEntity);
        saveAllAgreementPersons(agreement, agreementEntity);
        return agreementEntity;
    }

    private AgreementEntity saveAgreement(AgreementDTO agreement) {
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setUuid(UUID.randomUUID().toString());
        agreementEntity.setAgreementDateFrom(agreement.getAgreementDateFrom());
        agreementEntity.setAgreementDateTo(agreement.getAgreementDateTo());
        agreementEntity.setCountry(agreement.getCountry());
        agreementEntity.setAgreementPremium(agreement.getAgreementPremium());
        return agreementEntityRepository.save(agreementEntity);
    }

    private void saveAllSelectedRisks(AgreementDTO agreement, AgreementEntity agreementEntity) {
        List<SelectedRisksEntity> riskEntities = agreement.getSelectedRisks().stream()
                .map(riskIc -> {
                    SelectedRisksEntity riskEntity = new SelectedRisksEntity();
                    riskEntity.setAgreement(agreementEntity);
                    riskEntity.setRiskIc(riskIc);
                    return riskEntity;
                })
                .collect(Collectors.toList());

        selectedRisksEntityRepository.saveAll(riskEntities);
    }

    private void saveAllAgreementPersons(AgreementDTO agreement, AgreementEntity agreementEntity) {
        for (PersonDTO personDTO : agreement.getPersons()) {
            PersonEntity personEntity = personEntityFactory.createPersonEntity(personDTO);

            AgreementPersonEntity agreementPersonEntity = saveAgreementPerson(agreementEntity, personDTO, personEntity);

            List<AgreementPersonRisksEntity> risks = personDTO.getRisks().stream()
                    .map(riskDTO -> {
                        AgreementPersonRisksEntity agreementPersonRisksEntity = new AgreementPersonRisksEntity();
                        agreementPersonRisksEntity.setAgreementPerson(agreementPersonEntity);
                        agreementPersonRisksEntity.setRiskIc(riskDTO.getRiskIc());
                        agreementPersonRisksEntity.setPremium(riskDTO.getPremium());
                        return agreementPersonRisksEntity;
                    })
                    .collect(Collectors.toList());

            agreementPersonRisksEntityRepository.saveAll(risks);
        }
    }

    private AgreementPersonEntity saveAgreementPerson(AgreementEntity agreementEntity, PersonDTO person, PersonEntity personEntity) {
        AgreementPersonEntity agreementPersonEntity = new AgreementPersonEntity();
        agreementPersonEntity.setAgreement(agreementEntity);
        agreementPersonEntity.setPerson(personEntity);
        agreementPersonEntity.setMedicalRiskLimitLevel(person.getMedicalRiskLimitLevel());
        agreementPersonEntity.setTravelCost(person.getTravelCost());
        return agreementPersonEntityRepository.save(agreementPersonEntity);
    }


}
