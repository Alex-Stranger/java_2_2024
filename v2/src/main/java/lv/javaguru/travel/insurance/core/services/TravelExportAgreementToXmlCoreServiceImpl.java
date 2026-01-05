package lv.javaguru.travel.insurance.core.services;

import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreResult;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import lv.javaguru.travel.insurance.core.domain.entities.AgreementXmlExportEntity;
import lv.javaguru.travel.insurance.core.repositories.entities.AgreementXmlExportEntityRepository;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Component
@Transactional
class TravelExportAgreementToXmlCoreServiceImpl implements TravelExportAgreementToXmlCoreService {

    @Value("${agreement.xml.exporter.job.path:}")
    private String exportPath;

    @Autowired
    private TravelGetAgreementService getAgreementService;

    @Autowired
    private AgreementXmlExportEntityRepository agreementXmlExportEntityRepository;

    @Autowired
    private ValidationErrorFactory validationErrorFactory;

    private static final Logger logger = LoggerFactory.getLogger(TravelExportAgreementToXmlCoreServiceImpl.class);

    @Override
    public TravelExportAgreementToXmlCoreResult exportAgreement(TravelExportAgreementToXmlCoreCommand command) {

        String uuid = command.getUuid();

        try {
            AgreementDTO agreement = getAgreement(uuid);
            String xml = convertToXml(agreement);
            saveToFile(uuid, xml);
            saveToDatabaseExportedAgreement(uuid);

            return new TravelExportAgreementToXmlCoreResult();

        } catch (JAXBException e) {
            logger.error("XML conversion failed for agreement {}", uuid);
            return new TravelExportAgreementToXmlCoreResult(List.of(validationErrorFactory.buildError("ERROR_CODE_20")));
        } catch (IOException e) {
            logger.error("Failed to write XML file for agreement {}", uuid);
            return new TravelExportAgreementToXmlCoreResult(List.of(validationErrorFactory.buildError("ERROR_CODE_21")));
        } catch (Exception e) {
            logger.error("Unexpected error for agreement {}", uuid);
            return new TravelExportAgreementToXmlCoreResult(List.of(validationErrorFactory.buildError("ERROR_CODE_22")));
        }
    }

    private AgreementDTO getAgreement(String uuid) {
        TravelGetAgreementCoreResult coreResult = getAgreementService.getAgreement(new TravelGetAgreementCoreCommand(uuid));
        return coreResult.getAgreement();
    }

    private void saveToDatabaseExportedAgreement(String uuid) {
        AgreementXmlExportEntity exportEntity = new AgreementXmlExportEntity();
        exportEntity.setAgreementUuid(uuid);
        exportEntity.setAlreadyExported(Boolean.TRUE);
        agreementXmlExportEntityRepository.save(exportEntity);
    }

    private String convertToXml(AgreementDTO agreement) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(AgreementDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(agreement, stringWriter);
        return stringWriter.toString();
    }

    private void saveToFile(String uuid, String xml) throws IOException {
        String path = exportPath + "/agreement_" + uuid + ".xml";
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(xml);
        }
    }
}
