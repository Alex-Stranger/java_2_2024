package lv.javaguru.travel.insurance.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelCalculatePremiumRequestLoggerV1 {

    private static final Logger logger = LoggerFactory.getLogger(TravelCalculatePremiumRequestLoggerV1.class);

    public void log(TravelCalculatePremiumRequestV1 request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(request);
            logger.info("Request JSON: " + jsonString);
        } catch (Exception e) {
            logger.error("Error converting request to JSON", e);
        }
    }
}
