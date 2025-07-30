package lv.javaguru.travel.insurance.rest.internal;

import com.google.common.base.Stopwatch;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import lv.javaguru.travel.insurance.core.services.TravelGetAgreementService;
import lv.javaguru.travel.insurance.dto.internal.GetAgreementDTOConverter;
import lv.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import lv.javaguru.travel.insurance.rest.common.TravelRestRequestExecutionTimeLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel/api/internal/agreement")
public class TravelGetAgreementRestController {

    @Autowired
    private TravelRestRequestExecutionTimeLogger executionTimeLogger;
    @Autowired
    private TravelGetAgreementRequestLogger agreementRequestLogger;
    @Autowired
    private TravelGetAgreementResponseLogger agreementResponseLogger;
    @Autowired
    private GetAgreementDTOConverter dtoConverter;
    @Autowired
    private TravelGetAgreementService agreementService;

    @GetMapping(path = "/{uuid}",
    produces = "application/json")
    public TravelGetAgreementResponse getAgreement(@PathVariable String uuid) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TravelGetAgreementResponse response = processRequest(uuid);
        executionTimeLogger.logExecutionTime(stopwatch);
        return response;
    }

    private TravelGetAgreementResponse processRequest(String uuid) {
        agreementRequestLogger.log(uuid);
        TravelGetAgreementCoreCommand coreCommand = dtoConverter.buildCoreCommand(uuid);
        TravelGetAgreementCoreResult coreResult = agreementService.getAgreement(coreCommand);
        TravelGetAgreementResponse response = dtoConverter.buildResponse(coreResult);
        agreementResponseLogger.log(response);
        return response;
    }
}
