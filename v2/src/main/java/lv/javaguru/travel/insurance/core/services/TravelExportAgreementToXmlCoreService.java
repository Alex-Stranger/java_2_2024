package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreResult;

public interface TravelExportAgreementToXmlCoreService {

    TravelExportAgreementToXmlCoreResult exportAgreement(TravelExportAgreementToXmlCoreCommand command);

}
