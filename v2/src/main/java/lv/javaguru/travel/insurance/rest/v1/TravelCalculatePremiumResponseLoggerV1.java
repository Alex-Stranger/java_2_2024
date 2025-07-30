package lv.javaguru.travel.insurance.rest.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelCalculatePremiumResponseLoggerV1 {

    private static final Logger logger = LoggerFactory.getLogger(TravelCalculatePremiumResponseLoggerV1.class);

    public void log(Object response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(response);
            logger.info("Response JSON: " + jsonString);
        } catch (Exception e) {
            logger.error("Error converting response to JSON", e);
        }
    }
}
