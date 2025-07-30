package lv.javaguru.travel.insurance.rest.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

import lv.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelCalculatePremiumRequestLoggerV2 {

    private static final Logger logger = LoggerFactory.getLogger(TravelCalculatePremiumRequestLoggerV2.class);

    public void log(TravelCalculatePremiumRequestV2 request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(request);
            logger.info("Request JSON: " + jsonString);
        } catch (Exception e) {
            logger.error("Error converting request to JSON", e);
        }
    }
}
