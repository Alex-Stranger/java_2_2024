package lv.javaguru.travel.insurance.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgreementXmlExporterJob {

    @Value("${agreement.xml.exporter.job.enabled:false}")
    private boolean agreementXmlExporterJobEnabled;

    private static final Logger logger = LoggerFactory.getLogger(AgreementXmlExporterJob.class);

    @Scheduled(fixedRate = 5000)
    public void execute() {
        logger.info("SimpleJob started");
        logger.info("SimpleJob finished");
    }

}
