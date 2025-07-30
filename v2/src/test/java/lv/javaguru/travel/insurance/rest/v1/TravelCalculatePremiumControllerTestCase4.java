package lv.javaguru.travel.insurance.rest.v1;

import org.junit.jupiter.api.Test;

public class TravelCalculatePremiumControllerTestCase4 extends TravelCalculatePremiumControllerV1Test {

    @Override
    public String getTestCaseFolderName() {
        return "test_case_4";
    }

    @Test
    public void execute() throws Exception {
        executeAndEvaluate(true);
    }
}
