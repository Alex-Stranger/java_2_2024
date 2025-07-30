package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.TravelAgreementUuidValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TravelGetAgreementServiceImpl implements TravelGetAgreementService{

    @Autowired
    private TravelAgreementUuidValidator agreementUuidValidator;

    @Autowired
    private AgreementDTOLoader agreementDTOLoader;

    @Override
    public TravelGetAgreementCoreResult getAgreement(TravelGetAgreementCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementUuidValidator.validate(command.getUuid());

        return errors.isEmpty()
                ? buildResponse(command.getUuid())
                : buildErrorResponse(errors);
    }

    private TravelGetAgreementCoreResult buildResponse(String uuid) {
        TravelGetAgreementCoreResult coreResult = new TravelGetAgreementCoreResult();
        coreResult.setAgreement(agreementDTOLoader.load(uuid));
        return coreResult;
    }

    private TravelGetAgreementCoreResult buildErrorResponse(List<ValidationErrorDTO> errors) {
        return new TravelGetAgreementCoreResult(errors);

    }
}
