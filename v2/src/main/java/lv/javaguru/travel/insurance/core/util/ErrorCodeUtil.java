package lv.javaguru.travel.insurance.core.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Component
public class ErrorCodeUtil {

    private Properties properties;

    public ErrorCodeUtil() {
        loadProperties();
    }

    private void loadProperties() {
        properties = new Properties();
        Resource resource = new ClassPathResource("errorCodes.properties");
        try (InputStream inputStream = resource.getInputStream()) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getErrorDescription(String errorCode) {
        return properties.getProperty(errorCode);
    }

    public String getErrorDescription(String errorCode, List<Placeholder> placeholders) {
        String description = properties.getProperty(errorCode);
        for (Placeholder placeholder : placeholders) {
            description = description.replace("{" + placeholder.getPlaceholderName() + "}", placeholder.getPlaceholderValue());
        }
        return description;
    }
}
