package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;

public interface TravelGetAllAgreementUuidsService {

    TravelGetAllAgreementUuidsCoreResult getUuids(TravelGetAllAgreementUuidsCoreCommand agreementUuidsCoreCommand);
}
