package lv.javaguru.travel.insurance.rest.v2;

import lv.javaguru.travel.insurance.rest.common.JsonFileReader;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class TravelCalculatePremiumControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonFileReader jsonFileReader;

    public abstract String getTestCaseFolderName();

    public void executeAndEvaluate() throws Exception {
        executeAndEvaluate
                (false);
    }

    public void executeAndEvaluate(boolean ignoreUUIDValue) throws Exception {
        executeAndEvaluate
                (getRequestFilePath(),
                 getResponseFilePath(),
                 ignoreUUIDValue
                );
    }

    private String getRequestFilePath() {
        return "rest/v2/" + getTestCaseFolderName() + "/request.json";
    }

    private String getResponseFilePath() {
        return "rest/v2/" + getTestCaseFolderName() + "/response.json";
    }

    private void executeAndEvaluate(String jsonRequestFilePath, String jsonResponseFilePath, boolean ignoreUUIDValue) throws Exception {
        String jsonRequest = jsonFileReader.readJsonFromFile(jsonRequestFilePath);
        MvcResult calculatedResult = mockMvc.perform(post("/insurance/travel/api/v2/")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = calculatedResult.getResponse().getContentAsString();
        String expectedJsonResponse = jsonFileReader.readJsonFromFile(jsonResponseFilePath);

        assertJson(responseContent)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .at("/uuid").isNotEmpty()
                .isEqualTo(expectedJsonResponse);
    }
}
