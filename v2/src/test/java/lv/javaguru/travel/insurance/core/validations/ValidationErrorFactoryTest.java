package lv.javaguru.travel.insurance.core.validations;

import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.ErrorCodeUtil;
import lv.javaguru.travel.insurance.core.util.Placeholder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationErrorFactoryTest {

    @Mock
    private ErrorCodeUtil errorCodeUtil;

    @InjectMocks
    private ValidationErrorFactory validationErrorFactory;

    @Test
    public void returnErrorWithDescription() {
        when(errorCodeUtil.getErrorDescription("ERROR_CODE")).thenReturn("error description");
        ValidationErrorDTO error = validationErrorFactory.buildError("ERROR_CODE");
        assertEquals("ERROR_CODE", error.getErrorCode());
        assertEquals("error description", error.getDescription());
    }

    @Test
    public void returnErrorWithDescriptionAndPlaceholders() {
        List<Placeholder> placeholders = List.of(new Placeholder("PLACEHOLDER", "AAA"));
        when(errorCodeUtil.getErrorDescription("ERROR_CODE", placeholders)).thenReturn("error AAA description");
        ValidationErrorDTO error = validationErrorFactory.buildError("ERROR_CODE", placeholders);
        assertEquals("ERROR_CODE", error.getErrorCode());
        assertEquals("error AAA description", error.getDescription());
    }
}
