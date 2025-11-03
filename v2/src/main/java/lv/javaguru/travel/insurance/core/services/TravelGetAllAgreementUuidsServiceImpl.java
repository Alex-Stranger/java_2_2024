package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
class TravelGetAllAgreementUuidsServiceImpl implements TravelGetAllAgreementUuidsService {

    @Autowired
    private AgreementEntityRepository agreementEntityRepository;

    @Override
    public TravelGetAllAgreementUuidsCoreResult getUuids(TravelGetAllAgreementUuidsCoreCommand agreementUuidsCoreCommand) {
        List<String> agreementUuids = agreementEntityRepository.findAll()
                                                               .stream()
                                                               .map(AgreementEntity::getUuid)
                                                               .collect(Collectors.toList());

        List<AgreementEntity> agreementEntities = agreementEntityRepository.findAll();
        for (AgreementEntity entity : agreementEntities) {
            List<String> agreementUuid1;
            agreementUuid1 = new ArrayList<>();
            agreementUuid1.add(entity.getUuid());

        }



        return new TravelGetAllAgreementUuidsCoreResult(null, agreementUuids);

    }

}
