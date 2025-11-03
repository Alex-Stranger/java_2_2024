package lv.javaguru.travel.insurance.rest.v2;

import lv.javaguru.travel.insurance.rest.common.JsonFileReader;
import lv.javaguru.travel.insurance.rest.v1.AgreementV1Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AgreementV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonFileReader jsonFileReader;

    @ParameterizedTest(name = "{0}")
    @MethodSource("testCaseProvider")
    void testAgreementCases(String caseFolder) throws Exception {
        String requestPath = "rest/v2/agreement/" + caseFolder + "/request.json";
        String responsePath = "rest/v2/agreement/" + caseFolder + "/response.json";

        String jsonRequest = jsonFileReader.readJsonFromFile(requestPath);
        String expectedResponse = jsonFileReader.readJsonFromFile(responsePath);

        MvcResult result = mockMvc.perform(post("/insurance/travel/api/v2/")
                        .content(jsonRequest)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();

        assertJson(actualResponse)
                .where()
                .keysInAnyOrder()
                .arrayInAnyOrder()
                .at("/uuid").isNotEmpty()
                .isEqualTo(expectedResponse);
    }

    static Stream<String> testCaseProvider() {
        try {
            Path basePath = Paths.get(Objects.requireNonNull(
                    AgreementV1Test.class.getClassLoader().getResource("rest/v2/agreement")
            ).toURI());

            return Files.list(basePath)
                    .filter(Files::isDirectory)
                    .map(path -> path.getFileName().toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to scan agreement test folders", e);
        }
    }
}
