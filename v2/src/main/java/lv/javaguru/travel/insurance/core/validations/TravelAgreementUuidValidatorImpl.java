package lv.javaguru.travel.insurance.core.validations;

import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import lv.javaguru.travel.insurance.core.util.Placeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class TravelAgreementUuidValidatorImpl implements TravelAgreementUuidValidator {

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    @Autowired
    private AgreementEntityRepository agreementEntityRepository;

    public List<ValidationErrorDTO> validate(String uuid) {
        if (uuid == null) {
            return List.of(validationErrorFactory.buildError("ERROR_CODE_17"));
        }else {
            if (agreementEntityRepository.findByUuid(uuid).isEmpty()) {
                Placeholder placeholder = new Placeholder("AGREEMENT_UUID", uuid);
            return List.of(validationErrorFactory.buildError("ERROR_CODE_18", List.of(placeholder)));

        }

        }
        return List.of();

    }


}

