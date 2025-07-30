package lv.javaguru.travel.insurance.web.v1;

import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import lv.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import lv.javaguru.travel.insurance.dto.v1.DtoV1Converter;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TravelInsuranceControllerV1Test {

    @Mock
    private TravelCalculatePremiumService calculatePremiumService;

    @Mock
    private DtoV1Converter dtoV1Converter;

    @InjectMocks
    private TravelInsuranceControllerV1 controller;

    private ModelMap modelMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMap = new ModelMap();
    }

    @Test
    void testShowForm() {
        String viewName = controller.showForm(modelMap);
        assertEquals("travel-calculate-premium-v1", viewName);
        assertEquals(TravelCalculatePremiumRequestV1.class, modelMap.get("request").getClass());
        assertEquals(TravelCalculatePremiumResponseV1.class, modelMap.get("response").getClass());
    }

    @Test
    void testProcessForm() {
        TravelCalculatePremiumRequestV1 request = new TravelCalculatePremiumRequestV1();
        TravelCalculatePremiumCoreCommand coreCommand = new TravelCalculatePremiumCoreCommand();
        TravelCalculatePremiumCoreResult coreResult = new TravelCalculatePremiumCoreResult();
        TravelCalculatePremiumResponseV1 response = new TravelCalculatePremiumResponseV1();

        when(dtoV1Converter.buildCoreCommand(any())).thenReturn(coreCommand);
        when(calculatePremiumService.calculatePremium(any())).thenReturn(coreResult);
        when(dtoV1Converter.buildResponse(any())).thenReturn(response);

        String viewName = controller.processForm(request, modelMap);

        assertEquals("travel-calculate-premium-v1", viewName);
        assertEquals(response, modelMap.get("response"));

        verify(dtoV1Converter, times(1)).buildCoreCommand(request);
        verify(calculatePremiumService, times(1)).calculatePremium(coreCommand);
        verify(dtoV1Converter, times(1)).buildResponse(coreResult);
    }
}
