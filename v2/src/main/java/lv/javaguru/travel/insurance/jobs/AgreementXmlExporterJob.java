package lv.javaguru.travel.insurance.jobs;

import lv.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import lv.javaguru.travel.insurance.core.services.TravelExportAgreementToXmlCoreService;
import lv.javaguru.travel.insurance.core.services.TravelGetNotExportedAgreementUuidsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class AgreementXmlExporterJob {

    @Value("${agreement.xml.exporter.job.enabled:false}")
    private boolean agreementXmlExporterJobEnabled;

    @Value("${agreement.xml.exporter.job.thread.count}")
    private int threadCount;

    @Autowired
    private TravelGetNotExportedAgreementUuidsService getNotExportedAgreementUuidsService;

    @Autowired
    private TravelExportAgreementToXmlCoreService exportAgreementToXmlCoreService;

    private volatile boolean shuttingDown = false;

    private ExecutorService executor;

    private static final Logger logger = LoggerFactory.getLogger(AgreementXmlExporterJob.class);

    @PreDestroy
    public void onShutdown() {
        logger.warn("Application is shutting down — stopping AgreementXmlExporterJob...");
        shuttingDown = true;
        shutdownAndAwaitTermination();
    }

    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    public void executeJob() {

        if (shuttingDown) {
            logger.warn("Skipping AgreementXmlExporterJob — application is shutting down.");
            return;
        }

        if (!agreementXmlExporterJobEnabled) {
            logger.info("Agreement XML export job is disabled — skipping execution.");
            return;
        }

        try {
            logger.info("Starting AgreementXmlExporterJob...");

            TravelGetNotExportedAgreementUuidsCoreResult uuidsCoreResult =
                    getNotExportedAgreementUuidsService.getUuids(new TravelGetNotExportedAgreementUuidsCoreCommand());

            List<String> uuids = uuidsCoreResult.getAgreementUuids();

            if (executor == null) {
                executor = Executors.newFixedThreadPool(threadCount);
            }

            List<Future<?>> futures = new ArrayList<>();

            for (String uuid : uuids) {
                futures.add(executor.submit(() -> exportAgreementToXmlCoreService.exportAgreement(new TravelExportAgreementToXmlCoreCommand(uuid))));
            }
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    logger.error("Error in async agreement processing", e);
                }
            }
        } catch (Exception e) {
            logger.error("Error executing AgreementXmlExporterJob", e);
        }
    }

    private void shutdownAndAwaitTermination() {
        if (executor == null) {
            logger.warn("Executor was never initialized — nothing to shut down.");
            return;
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("Executor did not terminate");
                }
            }
            logger.info("Finish AgreementXmlExporterJob...");

        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}