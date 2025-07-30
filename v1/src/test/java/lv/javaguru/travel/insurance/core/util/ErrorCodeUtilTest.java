package lv.javaguru.travel.insurance.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorCodeUtilTest {

    @Mock
    private Properties properties;

    @InjectMocks
    private ErrorCodeUtil errorCodeUtil;

    @Test
    public void getErrorDescription() {
        String errorCode = "ERROR_CODE";
        String expectedDescription = "error description";
        when(properties.getProperty("ERROR_CODE")).thenReturn("error description");
        String actualDescription = errorCodeUtil.getErrorDescription(errorCode);
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void getErrorDescriptionWithPlaceholders() {
        String errorCode = "ERROR_CODE";
        String descriptionWithPlaceholder = "error {PLACEHOLDER} description";
        when(properties.getProperty(errorCode)).thenReturn(descriptionWithPlaceholder);
        List<Placeholder> placeholders = List.of(new Placeholder("PLACEHOLDER", "AAA"));
        String actualDescription = errorCodeUtil.getErrorDescription(errorCode, placeholders);
        assertEquals("error AAA description", actualDescription);
    }
}
